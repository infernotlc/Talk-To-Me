package com.orion.talktome.composeui.Register.viewModel


import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.orion.talktome.helper.SharedPrefsHelper
import com.orion.talktome.models.Accounts
import com.orion.talktome.models.AccountsData
import com.orion.talktome.repository.AccountsRepository
import com.genband.mobile.NotificationStates
import com.genband.mobile.OnCompletionListener
import com.genband.mobile.RegistrationApplicationListener
import com.genband.mobile.RegistrationService
import com.genband.mobile.RegistrationStates
import com.genband.mobile.ServiceProvider
import com.genband.mobile.api.utilities.ICEServers
import com.genband.mobile.api.utilities.MobileError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.genband.mobile.api.utilities.*
import com.genband.mobile.api.utilities.Configuration

class RegisterViewModel(private val serviceProvider: ServiceProvider) : ViewModel() {


    private val accountsRepository = AccountsRepository()
    private val TAG = "RegisterViewModel"

    private val _accounts = MutableStateFlow<Accounts?>(null)
    val accounts: StateFlow<Accounts?> = _accounts

    private val _registrationStateChanged = MutableStateFlow<RegistrationStates?>(null)
    val registrationStateChanged: StateFlow<RegistrationStates?> = _registrationStateChanged

    private val _registrationDropped = MutableStateFlow<MobileError?>(null)
    val registrationDropped: StateFlow<MobileError?> = _registrationDropped

    private val _onInternalError = MutableStateFlow<MobileError?>(null)
    val onInternalError: StateFlow<MobileError?> = _onInternalError

    private val _registrationSuccess = MutableStateFlow<Boolean?>(null)
    val registrationSuccess: StateFlow<Boolean?> = _registrationSuccess

    private val _registrationFail = MutableStateFlow<MobileError?>(null)
    val registrationFail: StateFlow<MobileError?> = _registrationFail


    // Other State Flows for different UI states can be added as needed
    companion object{
        val notificationStateChanged = MutableLiveData<NotificationStates?>()
    }


    fun getAccountsFirebase() {
        accountsRepository.getAccounts { accounts ->
            // Use the MutableStateFlow's value property to update the state
            _accounts.value = accounts
        }
    }
    fun getMobileSdkVersion(): String {
        return serviceProvider.version
    }


    fun register() {
        val registrationService: RegistrationService = serviceProvider.registrationService

        val registrationApplicationListener = object : RegistrationApplicationListener {
            override fun registrationStateChanged(p0: RegistrationStates?) {
                _registrationStateChanged.value = p0
            }

            override fun registrationDropped(p0: MobileError?) {
                _registrationDropped.value = p0
            }

            override fun notificationStateChanged(p0: NotificationStates?) {
            }

            override fun onInternalError(p0: MobileError?) {
                _onInternalError.value = p0
            }
        }
        registrationService.setRegistrationApplicationListener(registrationApplicationListener)
        registrationService.registerToServer(3600, object : OnCompletionListener {
            override fun onSuccess() {
                _registrationSuccess.value = true
                SharedPrefsHelper.putBoolean(SharedPrefsHelper.SESSION_TOKEN, true)
                Log.d(TAG, "Registration Success")
            }

            override fun onFail(p0: MobileError?) {
                _registrationFail.value = p0
                Log.d(TAG, "Registration Fail: $p0")
            }
        })
    }

    fun setSharedPrefs(accountsData: AccountsData, useTurn: Boolean) {
        SharedPrefsHelper.putString(SharedPrefsHelper.DEVICE_USER, accountsData.device_user)
        SharedPrefsHelper.putString(SharedPrefsHelper.DEVICE_PASSWORD, accountsData.device_pass)
        SharedPrefsHelper.putString(SharedPrefsHelper.DEFAULT_DOMAIN, accountsData.default_domain)
        SharedPrefsHelper.putString(SharedPrefsHelper.REST_IP, accountsData.config.restServerIP)
        SharedPrefsHelper.putString(SharedPrefsHelper.REST_PORT, accountsData.config.restServerPort)
        SharedPrefsHelper.putString(
            SharedPrefsHelper.SOCKET_IP,
            accountsData.config.webSocketServerIP
        )
        SharedPrefsHelper.putString(
            SharedPrefsHelper.SOCKET_PORT,
            accountsData.config.webSocketServerPort
        )
        SharedPrefsHelper.putString(SharedPrefsHelper.PUSH_URL, accountsData.pushServerURL)
        SharedPrefsHelper.putInt(SharedPrefsHelper.ICE_TIMEOUT, accountsData.config.ICECollectionTimeout)
        SharedPrefsHelper.putStringSet(
            SharedPrefsHelper.TURN_ADDRESS,
            accountsData.ICEServers.servers.toSet()
        )
        SharedPrefsHelper.putBoolean(SharedPrefsHelper.USE_TURN, useTurn)
        SharedPrefsHelper.putBoolean(SharedPrefsHelper.SESSION_TOKEN, true)
    }
    fun setConfiguration(accountsData: AccountsData) {
        val configuration: Configuration = Configuration.getInstance()
        configuration.username = accountsData.device_user
        configuration.password = accountsData.device_pass
        configuration.restServerIp = accountsData.config.restServerIP
        configuration.restServerPort = accountsData.config.restServerPort.toInt()
        val iceServers = ICEServers()
        accountsData.ICEServers.servers.forEach {
            iceServers.addICEServer(it)
        }
        configuration.iceServers = iceServers
        configuration.webSocketServerIp = accountsData.config.webSocketServerIP
        configuration.webSocketServerPort = accountsData.config.webSocketServerPort.toInt()

        Log.d(ContentValues.TAG, "Configurations: " + printConfiguration())
    }


}

fun setAdvancedConfigurations(ringingFeedbackOption: String, tcpConnection: Boolean) {
    val configuration: Configuration = Configuration.getInstance()
    SharedPrefsHelper.putString(SharedPrefsHelper.RINGING_FEEDBACK_OPTION, ringingFeedbackOption)
    SharedPrefsHelper.putBoolean(SharedPrefsHelper.TCP_CONNECTION, tcpConnection)
    when (ringingFeedbackOption) {
        "APP" -> configuration.ringingFeedbackOption = RingingFeedbackOptions.APP
        "SERVER" -> configuration.ringingFeedbackOption = RingingFeedbackOptions.SERVER
        "AUTO" -> configuration.ringingFeedbackOption = RingingFeedbackOptions.AUTO
    }
}

private fun printConfiguration(): String {
    val configuration: Configuration = Configuration.getInstance()
    // return all fields of configuration each in a new line with the following format:
    // field_name: field_value
    return "" +
            "username: ${configuration.username}" +
            "password: ${configuration.password}" +
            "restServerIp: ${configuration.restServerIp}" +
            "restServerPort: ${configuration.restServerPort}" +
            "iceServers: ${configuration.iceServers}" +
            "webSocketServerIp: ${configuration.webSocketServerIp}" +
            "webSocketServerPort: ${configuration.webSocketServerPort}"
}


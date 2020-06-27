package com.weaponoid.uiguru.viewmodel

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.crowdfire.cfalertdialog.CFAlertDialog
import com.weaponoid.uiguru.R
import com.weaponoid.uiguru.util.UIDetailListener

class SettingsViewModel() : ViewModel(), UIDetailListener {

    override fun onClick(view: View) {

    }


    fun rateApp(context: Context?) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(
                "https://play.google.com/store/apps/details?id=com.weaponoid.uiguru"
            )
            setPackage("com.android.vending")
        }
        context?.startActivity(intent)
    }


    fun contact(activity: FragmentActivity) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, "akashchaudharymax@gmail.com")
        intent.putExtra(Intent.EXTRA_SUBJECT, "Regarding UI Guru App")
        if (intent.resolveActivity(activity.packageManager) != null) {
            activity.startActivity(intent)
        }


    }


    fun about(context: Context?) {
        val builder = CFAlertDialog.Builder(context, R.style.AlertDialogStyle)
        builder.setTitle(context?.getString(R.string.about))
        builder.setMessage("UI Guru is an Application which provides Adobe Xd, Sketch, Figma free UI/UX resources preview along with direct downloads")
            .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
            .setDialogBackgroundColor(Color.parseColor("#212121"))
            .setTextColor(Color.parseColor("#f2f2f2"))
            .setCornerRadius(20.0F)
            .show()


    }

    fun privacyPolicy(context: Context?) {
        val builder = CFAlertDialog.Builder(context, R.style.AlertDialogStyle)
        builder.setTitle(context?.getString(R.string.privacy))
        builder.setMessage(
            "Akash Chaudhary built the UI Guru app as a Free app. This SERVICE is provided by Akash Chaudhary at no cost and is intended for use as is.\n" +
                    "\n" +
                    "This page is used to inform visitors regarding my policies with the collection, use, and disclosure of Personal Information if anyone decided to use my Service.\n" +
                    "\n" +
                    "If you choose to use my Service, then you agree to the collection and use of information in relation to this policy. The Personal Information that I collect is used for providing and improving the Service. I will not use or share your information with anyone except as described in this Privacy Policy.\n" +
                    "\n" +
                    "The terms used in this Privacy Policy have the same meanings as in our Terms and Conditions, which is accessible at UI Guru unless otherwise defined in this Privacy Policy.\n" +
                    "\n" +
                    "Information Collection and Use\n" +
                    "\n" +
                    "For a better experience, while using our Service, I may require you to provide us with certain personally identifiable information. The information that I request will be retained on your device and is not collected by me in any way.\n" +
                    "\n" +
                    "The app does use third party services that may collect information used to identify you.\n" +
                    "\n" +
                    "Link to privacy policy of third party service providers used by the app\n" +
                    "\n" +
                    "Google Play Services\n" +
                    "Log Data\n" +
                    "\n" +
                    "I want to inform you that whenever you use my Service, in a case of an error in the app I collect data and information (through third party products) on your phone called Log Data. This Log Data may include information such as your device Internet Protocol (“IP”) address, device name, operating system version, the configuration of the app when utilizing my Service, the time and date of your use of the Service, and other statistics.\n" +
                    "\n" +
                    "Cookies\n" +
                    "\n" +
                    "Cookies are files with a small amount of data that are commonly used as anonymous unique identifiers. These are sent to your browser from the websites that you visit and are stored on your device's internal memory.\n" +
                    "\n" +
                    "This Service does not use these “cookies” explicitly. However, the app may use third party code and libraries that use “cookies” to collect information and improve their services. You have the option to either accept or refuse these cookies and know when a cookie is being sent to your device. If you choose to refuse our cookies, you may not be able to use some portions of this Service.\n" +
                    "\n" +
                    "Service Providers\n" +
                    "\n" +
                    "I may employ third-party companies and individuals due to the following reasons:\n" +
                    "\n" +
                    "To facilitate our Service;\n" +
                    "To provide the Service on our behalf;\n" +
                    "To perform Service-related services; or\n" +
                    "To assist us in analyzing how our Service is used.\n" +
                    "I want to inform users of this Service that these third parties have access to your Personal Information. The reason is to perform the tasks assigned to them on our behalf. However, they are obligated not to disclose or use the information for any other purpose.\n" +
                    "\n" +
                    "Security\n" +
                    "\n" +
                    "I value your trust in providing us your Personal Information, thus we are striving to use commercially acceptable means of protecting it. But remember that no method of transmission over the internet, or method of electronic storage is 100% secure and reliable, and I cannot guarantee its absolute security.\n" +
                    "\n" +
                    "Links to Other Sites\n" +
                    "\n" +
                    "This Service may contain links to other sites. If you click on a third-party link, you will be directed to that site. Note that these external sites are not operated by me. Therefore, I strongly advise you to review the Privacy Policy of these websites. I have no control over and assume no responsibility for the content, privacy policies, or practices of any third-party sites or services.\n" +
                    "\n" +
                    "Children’s Privacy\n" +
                    "\n" +
                    "These Services do not address anyone under the age of 13. I do not knowingly collect personally identifiable information from children under 13. In the case I discover that a child under 13 has provided me with personal information, I immediately delete this from our servers. If you are a parent or guardian and you are aware that your child has provided us with personal information, please contact me so that I will be able to do necessary actions.\n" +
                    "\n" +
                    "Changes to This Privacy Policy\n" +
                    "\n" +
                    "I may update our Privacy Policy from time to time. Thus, you are advised to review this page periodically for any changes. I will notify you of any changes by posting the new Privacy Policy on this page.\n" +
                    "\n" +
                    "This policy is effective as of 2020-04-19\n" +
                    "\n" +
                    "Contact Us\n" +
                    "\n" +
                    "If you have any questions or suggestions about my Privacy Policy, do not hesitate to contact me at akashchaudharymax@gmail.com."
        )
            .setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT)
            .setDialogBackgroundColor(Color.parseColor("#212121"))
            .setTextColor(Color.parseColor("#f2f2f2"))
            .setCornerRadius(20.0F)
            .show()
    }

}

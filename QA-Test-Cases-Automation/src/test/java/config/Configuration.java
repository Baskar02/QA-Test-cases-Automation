package config;

public class Configuration {
	public static String PATH_TO_GECKO_DRIVER = null;
	public static String PATH_TO_CHROME_DRIVER =null;
	public static String PATH_TO_IE_DRIVER =null;
	public static String PATH_TO_CN_CREDIT_PE=null;
	public static String PATH_TO_FILE_DOWNLOAD = null,PATH_VE_FILE_DOWNLOAD=null;
	public static String PATH_TO_DOWNLOAD_BROCHURE =null,PATH_TO_PAYMENT_CALCULATOR=null;
	public static String Lincoln_Cred_Nameplate = null;
	public static String Lincoln_Cred_Model = null;
	public static String Lincoln_BP_Nameplate = null;
	public static String Lincoln_BP_Model = null;
	public static String Ford_Cred_Nameplate = null;
	public static String Ford_Cred_Model = null;
	public static String Ford_BP_Nameplate = null;
	public static String Ford_BP_Model = null;
	
	static {
		if(System.getProperty("os.name").toLowerCase().contains("mac")) {
			System.out.println("Chrome driver initialised on: " + System.getProperty("os.name"));
			PATH_TO_FILE_DOWNLOAD = System.getProperty("user.home")+"/Downloads/build_and_price.pdf";
			PATH_TO_DOWNLOAD_BROCHURE = System.getProperty("user.home")+"/Downloads/";
			PATH_TO_PAYMENT_CALCULATOR = System.getProperty("user.home")+"/Downloads/payment_calculator.pdf";
			PATH_TO_CN_CREDIT_PE=System.getProperty("user.home")+"/Downloads/pdffile.pdf";
			PATH_VE_FILE_DOWNLOAD = System.getProperty("user.home")+"/Downloads/cotizalo.pdf";
		}else if(System.getProperty("os.name").toLowerCase().contains("linux")) {
			System.out.println("Chrome driver initialised on: " + System.getProperty("os.name"));			
			PATH_TO_FILE_DOWNLOAD = System.getProperty("user.home") + "/Downloads/build_and_price.pdf";
			PATH_TO_DOWNLOAD_BROCHURE = System.getProperty("user.home") + "/Downloads/";
			PATH_TO_PAYMENT_CALCULATOR = System.getProperty("user.home")+"/Downloads/payment_calculator.pdf";
			PATH_TO_CN_CREDIT_PE=System.getProperty("user.home")+"/Downloads/pdffile.pdf";
			PATH_VE_FILE_DOWNLOAD = System.getProperty("user.home")+"/Downloads/cotizalo.pdf";
		}
		else {
			System.out.println("Chrome driver initialised on: " + System.getProperty("os.name"));
			PATH_TO_FILE_DOWNLOAD = System.getProperty("user.home") + "\\Downloads\\build_and_price.pdf";
			PATH_TO_DOWNLOAD_BROCHURE = System.getProperty("user.home") + "\\Downloads\\";
			PATH_TO_PAYMENT_CALCULATOR = System.getProperty("user.home") + "\\Downloads\\payment_calculator.pdf";
			PATH_TO_CN_CREDIT_PE=System.getProperty("user.home")+"\\Downloads\\pdffile.pdf";
			PATH_VE_FILE_DOWNLOAD = System.getProperty("user.home")+"\\Downloads\\cotizalo.pdf";
		}
		Lincoln_Cred_Nameplate="林肯MKZ";
		Lincoln_Cred_Model="MKZ 尊享版";
		Lincoln_BP_Nameplate = "林肯MKZ";
		Lincoln_BP_Model = "MKZ 尊享版";
		Ford_Cred_Nameplate="林肯MKZ";
		Ford_Cred_Model="MKZ 尊悦版";
		Ford_BP_Nameplate = "林肯MKZ";
		Ford_BP_Model = "MKZ 尊悦版";
	}
//	public static final String PATH_TO_GECKO_DRIVER = "/Users/adminvml/Documents/TEST_AUTOMATION/Drivers/geckodriver";
//	public static final String PATH_TO_CHROME_DRIVER = "/Users/adminvml/Documents/TEST_AUTOMATION/Drivers/chromedriver";
//	public static final String PATH_TO_IE_DRIVER = "C:\\TEST_AUTOMATION\\DRIVERS\\IEDriverServer.exe";
//	
//	public static final String PATH_TO_FILE_DOWNLOAD = "/Users/adminvml/Downloads/build_and_price.pdf";
//	public static final String PATH_TO_DOWNLOAD_BROCHURE = "/Users/adminvml/Downloads/";
}

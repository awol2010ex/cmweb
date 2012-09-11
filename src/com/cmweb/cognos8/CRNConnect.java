package com.cmweb.cognos8;

import java.net.MalformedURLException;

import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Stub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cognos.developer.schemas.bibus._3.AgentService_Port;
import com.cognos.developer.schemas.bibus._3.AgentService_ServiceLocator;
import com.cognos.developer.schemas.bibus._3.BatchReportService_Port;
import com.cognos.developer.schemas.bibus._3.BatchReportService_ServiceLocator;
import com.cognos.developer.schemas.bibus._3.BiBusHeader;
import com.cognos.developer.schemas.bibus._3.ContentManagerService_Port;
import com.cognos.developer.schemas.bibus._3.ContentManagerService_ServiceLocator;
import com.cognos.developer.schemas.bibus._3.DataIntegrationService_Port;
import com.cognos.developer.schemas.bibus._3.DataIntegrationService_ServiceLocator;
import com.cognos.developer.schemas.bibus._3.DeliveryService_Port;
import com.cognos.developer.schemas.bibus._3.DeliveryService_ServiceLocator;
import com.cognos.developer.schemas.bibus._3.Dispatcher_Port;
import com.cognos.developer.schemas.bibus._3.Dispatcher_ServiceLocator;
import com.cognos.developer.schemas.bibus._3.EventManagementService_Port;
import com.cognos.developer.schemas.bibus._3.EventManagementService_ServiceLocator;
import com.cognos.developer.schemas.bibus._3.JobService_Port;
import com.cognos.developer.schemas.bibus._3.JobService_ServiceLocator;
import com.cognos.developer.schemas.bibus._3.MonitorService_Port;
import com.cognos.developer.schemas.bibus._3.MonitorService_ServiceLocator;
import com.cognos.developer.schemas.bibus._3.PropEnum;
import com.cognos.developer.schemas.bibus._3.QueryOptions;
import com.cognos.developer.schemas.bibus._3.ReportService_Port;
import com.cognos.developer.schemas.bibus._3.ReportService_ServiceLocator;
import com.cognos.developer.schemas.bibus._3.SearchPathMultipleObject;
import com.cognos.developer.schemas.bibus._3.Sort;
import com.cognos.developer.schemas.bibus._3.SystemService_Port;
import com.cognos.developer.schemas.bibus._3.SystemService_ServiceLocator;

public class CRNConnect 
{
	private final static Logger logger = LoggerFactory
	.getLogger(CRNConnect.class);
	// Create the objects that provide the connections to the IBM Cognos 8 services.

	// sn_dg_prm_smpl_connect_start_0
	private AgentService_ServiceLocator agentServiceLocator = null;
	// sn_dg_prm_smpl_connect_end_0
	private BatchReportService_ServiceLocator batchRepServiceLocator = null;
	private ContentManagerService_ServiceLocator cmServiceLocator = null;
	private DataIntegrationService_ServiceLocator dataIntServiceLocator = null;
	private DeliveryService_ServiceLocator deliveryServiceLocator = null;
	private EventManagementService_ServiceLocator eventMgmtServiceLocator = null;
	private JobService_ServiceLocator jobServiceLocator = null;
	private MonitorService_ServiceLocator monitorServiceLocator = null;
	private ReportService_ServiceLocator reportServiceLocator = null;
	private SystemService_ServiceLocator systemServiceLocator = null;
	private Dispatcher_ServiceLocator dispatcherServiceLocator=null;
	
	// There is an interface class for each IBM Cognos 8 service named
	// <servicename>_Port. The implementation class for each interface
	// is named <servicename>Stub. The stub class implements the methods
	// in the interface, and can be used to access the functionality provided
	// by the service. However, as it is a common practice, this sample
	// programs to the interfaces, instantiating instances of the
	// <servicename>_Port classes.

	// sn_dg_prm_smpl_connect_start_1
	private AgentService_Port agentService = null; 
	// sn_dg_prm_smpl_connect_end_1
	private BatchReportService_Port batchRepService = null;
	private ContentManagerService_Port cmService = null;
	private DataIntegrationService_Port dataIntService = null;
	private DeliveryService_Port deliveryService = null;
	private EventManagementService_Port eventMgmtService = null;
	private JobService_Port jobService = null;
	private MonitorService_Port monitorService = null;
	private ReportService_Port repService = null;
	private SystemService_Port sysService = null;
	private Dispatcher_Port dispatchService=null;
	
	// Set the location of the sample reports.
	private String curDir = System.getProperty("user.dir");
    private String CRN_HOME = curDir.substring(0,curDir.lastIndexOf("sdk")-1);
	private String REPORT_PATH = CRN_HOME + "/webcontent/samples";

	// Create a variable that contains the default URL for Content Manager.            
	// sn_dg_prm_smpl_connect_start_2
	public static String CM_URL = "http://localhost:9300/p2pd/servlet/dispatch";
	// sn_dg_prm_smpl_connect_end_2

	/**
	 * Use this method to connect to the IBM Cognos 8 server. The user will be
	 * prompted to confirm the Content Manager URL
	 *  
	 * @return		A connection to the server
	 */
	public ContentManagerService_Port connectToCognosServer()
	{
		BiBusHeader bibus = null;
		while (bibus == null)
		{

			// sn_dg_prm_smpl_connect_start_3
			// Create the service locators for IBM Cognos 8 services
			
			agentServiceLocator = new AgentService_ServiceLocator();
			// sn_dg_prm_smpl_connect_end_3
			batchRepServiceLocator = new BatchReportService_ServiceLocator();
			cmServiceLocator = new ContentManagerService_ServiceLocator();
			dataIntServiceLocator = new DataIntegrationService_ServiceLocator();
			deliveryServiceLocator = new DeliveryService_ServiceLocator();
			eventMgmtServiceLocator = new EventManagementService_ServiceLocator();
			jobServiceLocator = new JobService_ServiceLocator();
			monitorServiceLocator = new MonitorService_ServiceLocator();
			reportServiceLocator = new ReportService_ServiceLocator();
			systemServiceLocator = new SystemService_ServiceLocator();
			dispatcherServiceLocator=new Dispatcher_ServiceLocator();

			try
			{
				// sn_dg_prm_smpl_connect_start_4
				java.net.URL serverURL = new java.net.URL(CM_URL);
			
				//acquire references to IBM Cognos 8 Services
				
				agentService = agentServiceLocator.getagentService(serverURL);
				// sn_dg_prm_smpl_connect_end_4
				batchRepService = batchRepServiceLocator.getbatchReportService(serverURL);
				cmService = cmServiceLocator.getcontentManagerService(serverURL);
				dataIntService = dataIntServiceLocator.getdataIntegrationService(serverURL);
				deliveryService = deliveryServiceLocator.getdeliveryService(serverURL);
				eventMgmtService = eventMgmtServiceLocator.geteventManagementService(serverURL);
				jobService = jobServiceLocator.getjobService(serverURL);
				monitorService = monitorServiceLocator.getmonitorService(serverURL);
				repService = reportServiceLocator.getreportService(serverURL);
				sysService = systemServiceLocator.getsystemService(serverURL);
				dispatchService=dispatcherServiceLocator.getdispatcher(serverURL);
				
			} // ... catch expected exceptions after this point
			catch (MalformedURLException e)
			{
				logger.error("",e);
				return null;
			}
			catch (ServiceException e)
			{
				logger.error("",e);
				return null;
			}	

			try
			{
				cmService.query(
					new SearchPathMultipleObject("/"),
					new PropEnum[] {},
					new Sort[] {},
					new QueryOptions());
			}
			catch (java.rmi.RemoteException remoteEx)
			{
				logger.error("",remoteEx);
				//If authentication is required, this will generate an exception
				//At this point, this exception can safely be ignored
			}
			catch (java.lang.NullPointerException nullEx)
			{
				logger.error("",nullEx);
				return null;
			}

			// Retrieve the biBusHeader SOAP:Header that contains
			// the logon information.
			bibus =
				(BiBusHeader) ((Stub)cmService).getHeaderObject("", "biBusHeader");

			if (bibus != null)
			{
				return cmService;
			}
			
		}
		return null;
	}

	/**
	 * Use this method to connect to the IBM Cognos 8 server, bypassing any prompts.
	 * 
	 * @param CMURL	    The URL for the IBM Cognos 8 server
	 * @return			A connection to the IBM Cognos 8 server
	 */
	public ContentManagerService_Port connectToCognosServer(String CMURL)
	{
		CM_URL = CMURL;

		// Create the service locators for IBM Cognos 8 services
		
		agentServiceLocator = new AgentService_ServiceLocator();
		batchRepServiceLocator = new BatchReportService_ServiceLocator();
		cmServiceLocator = new ContentManagerService_ServiceLocator();
		dataIntServiceLocator = new DataIntegrationService_ServiceLocator();
		deliveryServiceLocator = new DeliveryService_ServiceLocator();
		eventMgmtServiceLocator = new EventManagementService_ServiceLocator();
		jobServiceLocator = new JobService_ServiceLocator();
		monitorServiceLocator = new MonitorService_ServiceLocator();
		reportServiceLocator = new ReportService_ServiceLocator();
		systemServiceLocator = new SystemService_ServiceLocator();
		dispatcherServiceLocator=new Dispatcher_ServiceLocator();

		
		try
		{
			java.net.URL serverURL = new java.net.URL(CMURL);
			
			//acquire references to IBM Cognos 8 services
			//
			
			agentService = agentServiceLocator.getagentService(serverURL);
			batchRepService = batchRepServiceLocator.getbatchReportService(serverURL);
			cmService = cmServiceLocator.getcontentManagerService(serverURL);
			dataIntService = dataIntServiceLocator.getdataIntegrationService(serverURL);
			deliveryService = deliveryServiceLocator.getdeliveryService(serverURL);
			eventMgmtService = eventMgmtServiceLocator.geteventManagementService(serverURL);
			jobService = jobServiceLocator.getjobService(serverURL);
			monitorService = monitorServiceLocator.getmonitorService(serverURL);
			repService = reportServiceLocator.getreportService(serverURL);
			sysService = systemServiceLocator.getsystemService(serverURL);
			dispatchService=dispatcherServiceLocator.getdispatcher(serverURL);
			
			return cmService;
		}
		//handle uncaught exceptions
		catch (MalformedURLException e)
		{
			logger.error("",e);
			return null;
		}
		catch (ServiceException e)
		{
			logger.error("",e);
			return null;
		}
	}
	
	public ContentManagerService_Port connectionChange(String endPoint)
	{
		try
		{
			java.net.URL endPointURL = new java.net.URL(endPoint);
						
			agentService = agentServiceLocator.getagentService(endPointURL);
			batchRepService = batchRepServiceLocator.getbatchReportService(endPointURL);
			cmService = cmServiceLocator.getcontentManagerService(endPointURL);
			dataIntService = dataIntServiceLocator.getdataIntegrationService(endPointURL);
			deliveryService = deliveryServiceLocator.getdeliveryService(endPointURL);
			eventMgmtService = eventMgmtServiceLocator.geteventManagementService(endPointURL);
			jobService = jobServiceLocator.getjobService(endPointURL);
			monitorService = monitorServiceLocator.getmonitorService(endPointURL);
			repService = reportServiceLocator.getreportService(endPointURL);
			sysService = systemServiceLocator.getsystemService(endPointURL);
			dispatchService=dispatcherServiceLocator.getdispatcher(endPointURL);
			
			return cmService;
		}
		catch (MalformedURLException eMalformed)
		{
			logger.error("",eMalformed);
			return null;
		}
		catch (ServiceException eService)
		{
			logger.error("",eService);
			return null;
		}
	}

	public String getDefaultSavePath()
	{
		return REPORT_PATH;
	}

	public void setDefaultSavePath(String newReportPath)
	{
		REPORT_PATH = newReportPath;
	}
	
	public AgentService_Port getAgentService()
	{
		BiBusHeader bibus = null;
		bibus =
			(BiBusHeader) ((Stub)agentService).getHeaderObject("", "biBusHeader");

		if (bibus == null) 
		{
			BiBusHeader CMbibus = null;
			CMbibus =
				(BiBusHeader) ((Stub)cmService).getHeaderObject("", "biBusHeader");

			((Stub)agentService).setHeader("", "biBusHeader", CMbibus);
		}
		return agentService;
	}
	
	public BatchReportService_Port getBatchRepService()
	{
		BiBusHeader bibus = null;
		bibus =
			(BiBusHeader) ((Stub)batchRepService).getHeaderObject("", "biBusHeader");

		if (bibus == null) 
		{
			BiBusHeader CMbibus = null;
			CMbibus =
				(BiBusHeader) ((Stub)cmService).getHeaderObject("", "biBusHeader");

			((Stub)batchRepService).setHeader("", "biBusHeader", CMbibus);
		}
		return batchRepService;
	}
	
	public ContentManagerService_Port getCMService()
	{		
		return cmService;
	}

	public DataIntegrationService_Port getDataIntService()
	{
		BiBusHeader bibus = null;
		bibus =
			(BiBusHeader) ((Stub)dataIntService).getHeaderObject("", "biBusHeader");

		if (bibus == null) 
		{
			BiBusHeader CMbibus = null;
			CMbibus =
				(BiBusHeader) ((Stub)cmService).getHeaderObject("", "biBusHeader");

			((Stub)dataIntService).setHeader("", "biBusHeader", CMbibus);
		}
		return dataIntService;
	}
	
	public DeliveryService_Port getDeliveryService()
	{
		BiBusHeader bibus = null;
		bibus =
			(BiBusHeader) ((Stub)deliveryService).getHeaderObject("", "biBusHeader");

		if (bibus == null) 
		{
			BiBusHeader CMbibus = null;
			CMbibus =
				(BiBusHeader) ((Stub)cmService).getHeaderObject("", "biBusHeader");

			((Stub)deliveryService).setHeader("", "biBusHeader", CMbibus);
		}
		return deliveryService;
	}
	
	public EventManagementService_Port getEventMgmtService()
	{
		BiBusHeader bibus = null;
		bibus =
			(BiBusHeader) ((Stub)eventMgmtService).getHeaderObject("", "biBusHeader");

		if (bibus == null) 
		{
			BiBusHeader CMbibus = null;
			CMbibus =
				(BiBusHeader) ((Stub)cmService).getHeaderObject("", "biBusHeader");

			((Stub)eventMgmtService).setHeader("", "biBusHeader", CMbibus);
		}
		return eventMgmtService;
	}
	
	public JobService_Port getJobService()
	{
		BiBusHeader bibus = null;
		bibus =
			(BiBusHeader) ((Stub)jobService).getHeaderObject("", "biBusHeader");

		if (bibus == null) 
		{
			BiBusHeader CMbibus = null;
			CMbibus =
				(BiBusHeader) ((Stub)cmService).getHeaderObject("", "biBusHeader");

			((Stub)jobService).setHeader("", "biBusHeader", CMbibus);
		}
		return jobService;
	}
	
	public MonitorService_Port getMonitorService()
	{
		BiBusHeader bibus = null;
		bibus =
			(BiBusHeader) ((Stub)monitorService).getHeaderObject("", "biBusHeader");

		if (bibus == null) 
		{
			BiBusHeader CMbibus = null;
			CMbibus =
				(BiBusHeader) ((Stub)cmService).getHeaderObject("", "biBusHeader");

			((Stub)monitorService).setHeader("", "biBusHeader", CMbibus);
		}
		return monitorService;
	}
	
	public ReportService_Port getReportService()
	{
		BiBusHeader bibus = null;
		bibus =
			(BiBusHeader) ((Stub)repService).getHeaderObject("", "biBusHeader");

		if (bibus == null) 
		{
			BiBusHeader CMbibus = null;
			CMbibus =
				(BiBusHeader) ((Stub)cmService).getHeaderObject("", "biBusHeader");

			((Stub)repService).setHeader("", "biBusHeader", CMbibus);
		}
		return repService;
	}

	public SystemService_Port getSystemService()
	{
		BiBusHeader bibus = null;
		bibus =
			(BiBusHeader) ((Stub)sysService).getHeaderObject("", "biBusHeader");

		if (bibus == null) 
		{
			BiBusHeader CMbibus = null;
			CMbibus =
				(BiBusHeader) ((Stub)cmService).getHeaderObject("", "biBusHeader");

			((Stub)sysService).setHeader("", "biBusHeader", CMbibus);
		}
		return sysService;
	}


	public Dispatcher_Port getDispatcherService()
	{
		BiBusHeader bibus = null;
		bibus =
			(BiBusHeader) ((Stub)dispatchService).getHeaderObject("", "biBusHeader");

		if (bibus == null) 
		{
			BiBusHeader CMbibus = null;
			CMbibus =
				(BiBusHeader) ((Stub)cmService).getHeaderObject("", "biBusHeader");

			((Stub)dispatchService).setHeader("", "biBusHeader", CMbibus);
		}
		return dispatchService;
	}

	
	
}

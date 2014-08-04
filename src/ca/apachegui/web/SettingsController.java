package ca.apachegui.web;

import apache.conf.parser.File;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.apachegui.db.Users;
import ca.apachegui.global.Constants;

/**
 * Servlet implementation class Settings
 */
@RestController
@RequestMapping("/Settings")
public class SettingsController {
	private static Logger log = Logger.getLogger(SettingsController.class);
	
	@RequestMapping(method=RequestMethod.GET,params="option=getAllSettingsNames",produces="application/json;charset=UTF-8")
	public String getAllSettingsNames() {
		
		JSONObject result = new JSONObject();
		result.put("init", Constants.init);
		result.put("confDirectory", Constants.confDirectory);
		result.put("confFile", Constants.confFile);
		result.put("serverRoot", Constants.serverRoot);
		result.put("logDirectory", Constants.logDirectory);
		result.put("modulesDirectory", Constants.modulesDirectory);
		result.put("binFile", Constants.binFile);
		result.put("username", Constants.username);
		result.put("password", Constants.password);
		result.put("processsInfoRefreshRate", Constants.processInfoRefreshRate);
		result.put("extendedStatus", Constants.extendedStatus);
		result.put("historyRetention", Constants.historyRetention);
		result.put("historyBuffer", Constants.historyBuffer);
		result.put("theme", Constants.theme);
		result.put("editorTheme", Constants.editorTheme);
		result.put("showTabs", Constants.showTabs);
		result.put("encoding", Constants.encoding);
		
		return result.toString();
	}
	
	@RequestMapping(method=RequestMethod.GET,params="option=getSetting",produces="application/json;charset=UTF-8")
	public String getSetting(@RequestParam(value="name") String name) {
		
		String value="";
		if(name.equals(Constants.username)) {
			value=Users.getUsername();
		}
		else {
			value=ca.apachegui.db.Settings.getSetting(name);
		}
		
		JSONObject result = new JSONObject();
		result.put("value", value);
		
		return result.toString();
		
	}

	@RequestMapping(method=RequestMethod.POST,params="option=setSetting",produces="application/json;charset=UTF-8")
	public String setSetting(@RequestParam(value="name") String name,
							 @RequestParam(value="value") String value) throws Exception {
	
		value=value.trim().replaceAll("\\\\", "/");
		
		if(name.equals(Constants.serverRoot))
		{
			if(value.endsWith("/"))
			{
				value=value.substring(0,value.length()-1);
			}
			log.trace("Setting serverRoot " + value);
			
			if(!(new File(value).exists()))
			{
				throw new Exception("The input directory does not exist");
			}
			
			if(!(new File(value).isDirectory()))
			{
				throw new Exception("The input directory is not a directory");
			}
		}
		if(name.equals(Constants.confDirectory))
		{
			if(value.endsWith("/"))
			{
				value=value.substring(0,value.length()-1);
			}
			log.trace("Setting confDirectory " + value);
			
			if(!(new File(value).exists()))
			{
				throw new Exception("The input directory does not exist");
			}
			
			if(!(new File(value).isDirectory()))
			{
				throw new Exception("The input directory is not a directory");
			}
		}
		if(name.equals(Constants.confFile))
		{
			log.trace("Setting confFile " + value);
			
			if(!(new File(value).exists()))
			{
				throw new Exception("The input file does not exist");
			}
			
			if((new File(value).isDirectory()))
			{
				throw new Exception("The input file is a directory");
			}
		}
		if(name.equals(Constants.logDirectory))
		{
			if(value.endsWith("/"))
			{
				value=value.substring(0,value.length()-1);
			}
			log.trace("Setting logDirectory " + value);
			
			if(!(new File(value).exists()))
			{
				throw new Exception("The input directory does not exist");
			}
			
			if(!(new File(value).isDirectory()))
			{
				throw new Exception("The input directory is not a directory");
			}
		}
		if(name.equals(Constants.modulesDirectory))
		{
			if(value.endsWith("/"))
			{
				value=value.substring(0,value.length()-1);
			}
			log.trace("Setting modulesDirectory " + value);
			
			if(!(new File(value).exists()))
			{
				throw new Exception("The input directory does not exist");
			}
			
			if(!(new File(value).isDirectory()))
			{
				throw new Exception("The input directory is not a directory");
			}
		}
		if(name.equals(Constants.binFile))
		{
			log.trace("Setting binFile " + value);
			
			if(!(new File(value).exists()))
			{
				throw new Exception("The input file does not exist");
			}
			
			if((new File(value).isDirectory()))
			{
				throw new Exception("The input file is a directory");
			}
		}
		if(name.equals(Constants.historyRetention) || name.equals(Constants.historyBuffer))
		{
			log.trace("Setting historyRetention/historyBuffer " + value);
			
			try
			{
				Integer.parseInt(value);
			}
			catch(Exception e)
			{
				throw new Exception("You must input a number");
			}
		}
		
		if(name.equals(Constants.username)) {
			Users.setUsername(value);
		}
		else if(name.equals(Constants.password)) {
			Users.setPassword(value);
		}
		else if(name.equals(Constants.confFile)) {
			ca.apachegui.db.Settings.setSetting(Constants.confFile, value);
		}
		else {
			ca.apachegui.db.Settings.setSetting(name, value);
		}	
		
		JSONObject result = new JSONObject();
		result.put("result", "success");
		return result.toString();
	}
	
}

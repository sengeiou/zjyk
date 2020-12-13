package com.lys.app;

import com.alibaba.fastjson.JSONObject;
import com.lys.app.config.AppConfig;
import com.lys.app.controller.ExamRecordController;
import com.lys.app.dao.DBDao;
import com.lys.app.interceptor.GlobalHandlerInterceptor;
import com.lys.utils.LOG;
import com.lys.utils.LOGJson;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@SpringBootApplication
@Controller
@MapperScan(basePackages = "com.lys.app.dao", annotationClass = Repository.class)
public class AppApplication implements ApplicationContextAware, WebMvcConfigurer
{
	public static Long startTime = 0L;
	public static Boolean stop = false;

	public static void main(String[] args)
	{
		SpringApplication.run(AppApplication.class, args);
		LOG.v("-------------------- ready ------------------");
//		RunServiceImpl.sServerState.startTime = System.currentTimeMillis();
		startTime = System.currentTimeMillis();
		stop = false;
	}

	@RequestMapping("/info")
	@ResponseBody
	public String prop(HttpSession session)
	{
		JSONObject obj = new JSONObject(true);

		obj.put("-session-", "---------------------------------------");
		obj.put("getUsername", AppConfig.getUsername(session));
		obj.put("getAuthorities", AppConfig.getAuthorities(session));

		obj.put("-getProperties-", "---------------------------------------");
		Properties properties = System.getProperties();
		for (String name : properties.stringPropertyNames())
		{
			obj.put(name, System.getProperty(name));
		}

		obj.put("-getenv-", "---------------------------------------");
		for (Map.Entry<String, String> entry : System.getenv().entrySet())
		{
			obj.put(entry.getKey(), entry.getValue());
		}

		return LOGJson.getStr(obj.toString(), true);
	}

	@Value("${url.root}")
	private String urlRoot = null;

	@RequestMapping("/config")
	@ResponseBody
	public Map<String, Object> config()
	{
		Map<String, Object> result = new HashMap<>();
		result.put("urlRoot", urlRoot);
		return result;
	}

	@GetMapping("/book")
	public String book()
	{
		return "book";
	}

	@GetMapping("/mst")
	public String mst()
	{
		return "mst";
	}

	@GetMapping("/admin")
	public String admin()
	{
		return "admin";
	}

	@GetMapping("/exam")
	public String exam()
	{
		return "exam";
	}

	@GetMapping("/cloud")
	public String cloud()
	{
		return "jsp/cloud";
	}

	@GetMapping(value = "/td/{param}")
	public ModelAndView taskdetail(@PathVariable("param") String param)
	{
		String taskId = "";
		String taskPage = "";

		int pos = param.indexOf("_");
		if (pos > 0)
		{
			taskId = param.substring(0, pos);
			taskPage = param.substring(pos + 1);
		}
		else
		{
			taskId = param;
		}

		LOG.v("taskdetail " + param + ", " + taskId + ", " + taskPage);

		ModelAndView mv = new ModelAndView();
		mv.setViewName("jsp/taskdetail");
		mv.addObject("urlRoot", urlRoot);
		mv.addObject("taskId", taskId);
		mv.addObject("taskPage", taskPage);
		return mv;
	}

	@Autowired
	private ExamRecordController examRecordController = null;

	@GetMapping(value = "/examdp/{id}")
	public ModelAndView examdp(@PathVariable("id") String id)
	{
		return examRecordController.examdp(id);
	}

	@GetMapping(value = "/examkq/{id}")
	public ModelAndView examkq(@PathVariable("id") String id)
	{
		return examRecordController.examkq(id);
	}

	@Autowired
	private Environment env = null;

	private boolean isDev()
	{
		return env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev");
	}

	public static boolean isWindows()
	{
		return System.getProperty("os.name").toLowerCase().contains("windows");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
	{
		LOG.v("-------------------- main --------- setApplicationContext ---------");
		if (false)
		{
			LOG.v("检查环境匹配。。。");
			if (isDev() != isWindows())
			{
				LOG.v("环境不匹配");
				System.exit(-2);
			}
		}
	}

	@Autowired
	private DBDao dbDao = null;

	@PostConstruct
	public void init()
	{
		LOG.v("-------------------- main --------- init ---------");
		startTime = System.currentTimeMillis();
		stop = false;
//		dbDao.createDB();
//		dbDao.useDB();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry)
	{
		InterceptorRegistration ir = registry.addInterceptor(new GlobalHandlerInterceptor());
	}
}

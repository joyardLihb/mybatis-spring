package tk.mybatis.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import tk.mybatis.web.model.SysDict;
import tk.mybatis.web.service.DictService;
/**
 * 字典控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="/dicts")
public class DictController {
	@Autowired
	private DictService dictService; 
	
	@RequestMapping
	public ModelAndView dicts(SysDict sysDict, Integer offset, Integer limit){
		ModelAndView view = new ModelAndView("dicts");
		List<SysDict> dicts = dictService.findBySysDict(sysDict, offset, limit);
		view.addObject("dicts", dicts);
		return view;
	}
	/**
	 * 新增或修改信息页面，通过get请求跳转页面
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.GET)
	public ModelAndView add(Long id){
		ModelAndView view = new ModelAndView("dict_add");
		SysDict sysDict = null;
		if(id == null){
			sysDict = new SysDict();
		}
		else{
			sysDict = dictService.findById(id);
		}
		view.addObject("model", sysDict);
		return view;
	}
	/**
	 * 新增或修改信息页面
	 * @return
	 */
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public ModelAndView save(SysDict sysDict){//POST请求请会自动将表单参数绑定到SysDict对象上
		ModelAndView view = new ModelAndView();
		try{
			dictService.saveOrUpdate(sysDict);
			//注意是redirect:/dicts，不要只是dicts，/dicts会从服务器根路径http://localhost:8080/mybatis-spring/开始找http://localhost:8080/mybatis-spring/dicts.jsp
			//如果有是redirect:dicts,则会从当前请求路径开始找http://localhost:8080/mybatis-spring/dicts，找到http://localhost:8080/mybatis-spring/dicts/dicts.jsp
			//会找不到而报错
			view.setViewName("redirect:/dicts");
		}catch(Exception e){
			view.setViewName("dict_add");
			view.addObject("msg", e.getMessage());
			view.addObject("model", sysDict);
		}
		return view;
	}
	/**
	 * 删除字典项
	 * @return
	 */
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	@ResponseBody//REST风格，将返回的对象通过Jackson自动转换为json格式返回到前台页面，如果不加此注解，控制器会找跟请求对应的delete.jsp，找不到报404错误
	public ModelMap delete(Long id){
		ModelMap view = new ModelMap();
		try{
			boolean success = dictService.deleteById(id);
			view.addAttribute("success",success);
		}catch(Exception e){
			view.addAttribute("success", false);
			view.addAttribute("msg", e.getMessage());
		}
		return view;
	}
}

package tk.mybatis.web.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
	@RequestMapping(value={"","/index"})
	public ModelAndView dicts(){
		ModelAndView view = new ModelAndView("index");
		view.addObject("now", new Date());
		return view;
	}
}

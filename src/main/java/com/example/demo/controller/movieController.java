package com.example.demo.controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import dto.movieDto;

@Controller
public class movieController {
	@Autowired
	JdbcTemplate jdbcTemplate;

//    @RequestMapping("home")
//    public ModelAndView home(){
//    	ModelAndView mv = new ModelAndView();
//    	mv.setViewName("home");
//    	
//    	
//    	return mv;
//    }
	@SuppressWarnings("null")
	@RequestMapping("home")
	public ModelAndView home(HttpSession session) {
		//ModelAndView mv = new ModelAndView();
		
		//mv.
		System.out.println("Reading movie records...");
		System.out.printf("%-30.30s  %-30.30s%n", "Title", "Description");
		
//		List<String> list = jdbcTemplate.queryForList("SELECT * FROM movies",String.class);
//		session.setAttribute("list", list);
		
		LinkedList<movieDto> list = new LinkedList<movieDto>();
				
		jdbcTemplate.query("SELECT * FROM movies", (rs)-> {
			System.out.printf("%-30.30s  %-30.30s%n", rs.getString("title"), rs.getString("description"));
			//home.addObject("title",rs.getString("title") );
			movieDto temp = new movieDto(rs.getString("title"),rs.getString("description"));
			list.add(temp);
			//session.setAttribute("title", rs.getString("title"));
			//session.setAttribute("description", rs.getString("description"));
		});
	    ModelAndView map = new ModelAndView("index");
	    map.addObject("list", list);
	    map.setViewName("home.jsp");
		
		//session.setAttribute("list", list);
		
		
		
		return map;
	}

}

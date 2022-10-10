package com.jasvindersingh.airlinebookingsystem.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.jasvindersingh.airlinebookingsystem.constants.AppConstants;
import com.jasvindersingh.airlinebookingsystem.exceptions.AppException;
import com.jasvindersingh.airlinebookingsystem.models.Airline;
import com.jasvindersingh.airlinebookingsystem.models.Booking;
import com.jasvindersingh.airlinebookingsystem.models.Hotel;
import com.jasvindersingh.airlinebookingsystem.models.User;
import com.jasvindersingh.airlinebookingsystem.services.IAirlineService;
import com.jasvindersingh.airlinebookingsystem.services.IBookingService;
import com.jasvindersingh.airlinebookingsystem.services.IHotelService;

@Controller
public class AppController {
	@Autowired
	private IHotelService hotelService;
	
	@Autowired
	private IAirlineService airlineService;
	
	@Autowired
	private IBookingService bookingService;
	
	@RequestMapping(value="/index")
	public String index() {
		return AppConstants.PAGE_INDEX;
	}
	
	@RequestMapping(value="/login")
	public String login(Model model) {
		model.addAttribute("user",new User());
		return AppConstants.PAGE_LOGIN;
	}
	
	@RequestMapping(value="/register")
	public String register(Model model) {
		model.addAttribute("user",new User());
		return AppConstants.PAGE_REGISTRATION;
	}
	
	@RequestMapping(value="/hotel")
	public String hotel(Model model) throws AppException {
		List<Hotel> lst = hotelService.getAll();
		model.addAttribute("hotel",new Hotel());
		model.addAttribute("hotelList",lst);
		return AppConstants.PAGE_HOTEL;
	}
	
	@RequestMapping(value="/airline")
	public String airline(Model model) throws AppException {
		List<Airline> lst = airlineService.getAll();
		model.addAttribute("airline",new Airline());
		model.addAttribute("airlineList",lst);
		return AppConstants.PAGE_AIRLINE;
	}
	
	@RequestMapping(value="/booking")
	public String newbooking(Model model) {
		return AppConstants.PAGE_BOOKING;
	}
	
	@RequestMapping(value="/allbookings")
	public ModelAndView bookings(HttpSession session ) throws AppException {
		ModelAndView mav = new ModelAndView(AppConstants.PAGE_ALL_BOOKING);
		if(session.getAttribute("currentBooking") != null) {
			mav.addObject("booking", (Booking)session.getAttribute("currentBooking"));
			//session.removeAttribute("currentBooking");
		}
		else {
			mav.addObject("booking",new Booking());
		}
		User user = (User)session.getAttribute("user");
		List<Booking> lst = bookingService.getAllByUserID(user.getId());
		mav.addObject("bookingList",lst);
		return mav;
	}
}

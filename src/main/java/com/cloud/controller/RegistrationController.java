package com.cloud.controller;


import com.cloud.model.Attendee;
import com.cloud.service.AttendeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


/**
 * Created by zahid on 3/21/2018.
 */
@Controller
class RegistrationController {

    @Autowired
    AttendeeService attendeeService;


    @RequestMapping(value = "/landing", method = RequestMethod.GET)
    public String myLanding(Model model) {
        model.addAttribute("attendee", new Attendee());
        return "landing";
    }

    @RequestMapping(value = "/officers", method = RequestMethod.GET)
    public String officers1(Model model) {
        model.addAttribute("attendee", new Attendee());
        return "officers";
    }

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public String events1(Model model) {
        model.addAttribute("attendee", new Attendee());
        return "events";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    String home(Model model) {
        model.addAttribute("attendee", attendeeService.getAttendee());
        return "admin";
    }
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String conference(Model model) {
        model.addAttribute("attendee", new Attendee());
        return "program";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@ModelAttribute Attendee attendee, RedirectAttributes redirectAttributes)
    {
        attendeeService.addAttendee(attendee);
        redirectAttributes.addFlashAttribute("flash", "Registered "+ attendee.getEmail());
        return "redirect:/";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    ModelAndView addAttendee(@RequestParam String email) throws Exception {

        //System.out.println("Received request for student");
        ModelAndView modelAndView = new ModelAndView("admin");
        try {
            Attendee attendee = new Attendee();
            attendee.setEmail(email);
            attendee = attendeeService.addAttendee(attendee);
            modelAndView.addObject("message", "Member added with email: " + attendee.getEmail());
        }
        catch (Exception ex){
            modelAndView.addObject("message", "Failed to add Member: " + ex.getMessage());
        }
        modelAndView.addObject("attendee", attendeeService.getAttendee());
        return modelAndView;
    }
}
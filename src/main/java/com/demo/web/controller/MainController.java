package com.demo.web.controller;

import com.demo.model.CurrencyType;
import com.demo.service.CurrencyConverterService;
import com.demo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.demo.model.User;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.math.BigDecimal;

@Controller
public class MainController {

    private final Logger logger = LoggerFactory.getLogger(MainController.class);

    private UserService userService;
    private CurrencyConverterService currencyConverterService;

    @Autowired
    public MainController(UserService userService, CurrencyConverterService currencyConverterService) {
        this.userService = userService;
        this.currencyConverterService = currencyConverterService;

    }


    @GetMapping(path={"/", "/login"})
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping(path="/registration")
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("user", new User());
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView  createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getEmail());
        if (userExists != null) {
            bindingResult
                    .rejectValue("email", "error.user",
                            "There is already a user registered with the login provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage",  "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");
        }
        return modelAndView;
    }

    @GetMapping(path="/user/home")
    public ModelAndView home(){

        logger.info("INFO: home() is alive!");

        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        modelAndView.addObject("username", "Welcome " + user.getLogin() + " (" + user.getEmail() + ")");
        modelAndView.addObject("adminMessage","Welcome");
        modelAndView.addObject("balanceUSD", userService.getBalanceByCurrencyType(user, CurrencyType.USD));
        modelAndView.addObject("balanceEUR", userService.getBalanceByCurrencyType(user, CurrencyType.EUR));
        modelAndView.addObject("balanceRUR", userService.getBalanceByCurrencyType(user, CurrencyType.RUR));
        modelAndView.addObject("currencyUSD", CurrencyType.USD);
        modelAndView.addObject("currencyRUR", CurrencyType.RUR);
        modelAndView.addObject("currencyEUR", CurrencyType.EUR);
//        modelAndView.addObject("amountTo", currencyConverterService.getRate(currencyConverterService.findCurrency( CurrencyType.USD),
//                currencyConverterService.findCurrency( CurrencyType.EUR)));

        modelAndView.setViewName("user/home");

        logger.info("INFO: modelAndView.getModel: " + modelAndView.getModel());

        return modelAndView;
    }

    @GetMapping(value = "/getRate")
    public ModelAndView  getCurrencyForRate(@RequestParam("amountFrom") String amountFrom,
                                            @RequestParam("currencyFrom") String currencyFrom,
                                            @RequestParam("currencyTo") String currencyTo,
                                            @RequestParam("amountTo") String amountTo) {

        logger.info("INFO: getCurrencyForRate() is alive");
        logger.info("INFO: amountFrom: " + amountFrom);

        ModelAndView modelAndView = new ModelAndView();

        Float newRate = currencyConverterService.getRate(currencyConverterService.findCurrency(currencyFrom),
                                                            currencyConverterService.findCurrency(currencyTo));
        BigDecimal amount = new BigDecimal(amountFrom);
        BigDecimal newAmount = currencyConverterService.calculateCurrencyCurse(amount, newRate);

        logger.info("newAmount: " + newAmount);

        amountTo = newAmount.toString();
        logger.info("INFO: amountTo: " + amountTo);

//        modelAndView.addObject("amountTo", newAmount);
//        modelAndView.addObject("amountTo", newAmount.toString());
        modelAndView.addObject("amountTo", amountTo);
        modelAndView.setViewName("user/home");
        return modelAndView;
    }
}

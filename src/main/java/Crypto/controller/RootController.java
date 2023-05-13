package Crypto.controller;

import Crypto.model.IndicatorRequest;
import Crypto.model.ValueRequest;
import Crypto.service.IndicatorService;
import Crypto.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@RequiredArgsConstructor
@RestController
public class RootController {
    private final IndicatorService indicatorService;

    @GetMapping("/")
    public ModelAndView getDefaultInfo() {
        return new ModelAndView("root-page");
    }

    @GetMapping("/api/v1/getValues")
    public ModelAndView getValues() {
        ModelAndView result = new ModelAndView("get-values");
        result.addObject("value", new ValueRequest());
        return result;
    }

    @PostMapping("/api/v1/getValues")
    public ModelAndView getValues(@ModelAttribute ValueRequest values) {
        String currency = values.getCurrency();
        String startDate = values.getStartDate();
        String endDate = values.getEndDate();

        if (currency == null || !Utils.isValidDates(startDate, endDate)) {
            return new ModelAndView("error/400");
        } else {
            return currency.equalsIgnoreCase("btc")
                    ? new ModelAndView(String.format("redirect:/api/v1/btc/values?startDate=%s&endDate=%s", startDate, endDate))
                    : currency.equalsIgnoreCase("doge")
                    ? new ModelAndView(String.format("redirect:/api/v1/doge/values?startDate=%s&endDate=%s", startDate, endDate))
                    : currency.equalsIgnoreCase("eth")
                    ? new ModelAndView(String.format("redirect:/api/v1/eth/values?startDate=%s&endDate=%s", startDate, endDate))
                    : currency.equalsIgnoreCase("ltc")
                    ? new ModelAndView(String.format("redirect:/api/v1/ltc/values?startDate=%s&endDate=%s", startDate, endDate))
                    : currency.equalsIgnoreCase("xrp")
                    ? new ModelAndView(String.format("redirect:/api/v1/xrp/values?startDate=%s&endDate=%s", startDate, endDate))
                    : new ModelAndView("error/400");
        }
    }

    @GetMapping("/api/v1/getIndicator")
    public ModelAndView getCurrencyByIndicator() {
        ModelAndView result = new ModelAndView("find-suitable");
        result.addObject("body", new IndicatorRequest());
        return result;
    }

    @PostMapping("/api/v1/getIndicator")
    public ModelAndView getCurrencyByIndicator(@ModelAttribute IndicatorRequest body) {
        String indicator = body.getIndicator();
        String value = body.getValue();
        String startDate = body.getStartDate();
        String endDate = body.getEndDate();

        if (indicator == null || indicator.equals("0") || !Utils.isValidNumber(value) || !Utils.isValidDates(startDate, endDate)) {
            return new ModelAndView("error/400");
        } else {
            String response = indicatorService.getCurrencyByIndicator(indicator, value, startDate, endDate);
            ModelAndView result = new ModelAndView("result");
            result.addObject("result", response);
            return result;
        }
    }
}

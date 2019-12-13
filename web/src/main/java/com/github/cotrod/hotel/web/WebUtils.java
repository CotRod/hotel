package com.github.cotrod.hotel.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;

public class WebUtils {
    private static final Logger log = LoggerFactory.getLogger(WebUtils.class);

    public static void setPageNumber(ModelMap modelMap) {
        String navigation = (String) modelMap.getAttribute("nav");
        if (navigation == null) {
            return;
        }
        int currentPage = (int) modelMap.getAttribute("pageNum");
        if (navigation.equals("next")) {
            currentPage++;
        } else if (navigation.equals("prev")) {
            currentPage--;
        }
        modelMap.addAttribute("pageNum", currentPage);
    }
}

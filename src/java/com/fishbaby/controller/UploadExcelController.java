package com.fishbaby.controller;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by yuminghui3 on 2017/6/4.
 */
@RequestMapping("/uploadExcel")
public class UploadExcelController implements BaseController {

    @RequestMapping("/upload.do")
    public ModelAndView upload(ModelMap model, @RequestParam MultipartFile file){
        model.addAttribute("success", false);
        model.addAttribute("repeat", false);
        return new ModelAndView();
    }
}

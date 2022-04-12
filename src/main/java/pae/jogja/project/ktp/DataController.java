/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pae.jogja.project.ktp;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author user
 */
@Controller
public class DataController {
    //DataJpaController datactrl = new DataJpaController();
    //List<Data> newdata = new ArrayList<>();
    
    @RequestMapping("/data")
    @ResponseBody
    public String getDataKTP(Model model){
        
        try{
            //newdata = datactrl.findDataEntities(0,0);
        }
        catch(Exception e){
            //model.addAttribute("goData", datactrl);
        }
        return "database";
    }
}

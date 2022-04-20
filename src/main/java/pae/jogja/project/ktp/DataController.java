/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pae.jogja.project.ktp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pae.jogja.project.ktp.exceptions.NonexistentEntityException;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author user
 */
@Controller
public class DataController {

    DataJpaController datactrl = new DataJpaController();
    Data data = new Data();
    List<Data> newdata = new ArrayList<>();

    @RequestMapping("/data")
    //@ResponseBody
    public String getDataKTP(Model model) {

        int record = datactrl.getDataCount();

        try {
            newdata = datactrl.findDataEntities().subList(0, record);
        } catch (Exception e) {
        }
        model.addAttribute("goData", newdata);
        model.addAttribute("record", record);

        return "database";

    }

    @RequestMapping("/create")
    public String createData() {

        return "create";
    }

    @RequestMapping("/newdata")
    public String newData(HttpServletRequest data) throws ParseException, Exception {

        Data datadat = new Data();

        String idInput = data.getParameter("id");
        int iid = Integer.parseInt(idInput);
        String nonik = data.getParameter("noktp");
        String namaInput = data.getParameter("name");
        String tanggal = data.getParameter("tanggal");
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(tanggal);
        String jenis = data.getParameter("jenis");
        String alamatInput = data.getParameter("alamat");
        String agama = data.getParameter("agama");
        String status = data.getParameter("status");
        String pekerjaan = data.getParameter("kerja");
        String warganegara = data.getParameter("warganegara");
        String berlaku = "Seumur Hidup";

        //String filename = StringUtils.cleanPath(file.getOriginalFilename());
        //filename = filename.toLowerCase().replaceAll(" ", "-");
        //byte[] image = file.getBytes();
        datadat.setId(iid);
        datadat.setNoktp(nonik);
        datadat.setNama(namaInput);
        datadat.setTgllahir(date);
        datadat.setJeniskelamin(jenis);
        datadat.setAlamat(alamatInput);
        datadat.setAgama(agama);
        datadat.setStatus(status);
        datadat.setPekerjaan(pekerjaan);
        datadat.setKewarganegaraan(warganegara);
        datadat.setBerlakuhingga(berlaku);
        //datadat.setFoto(image);

        datactrl.create(datadat);

        return "database";
    }

    @RequestMapping("/edit")
    public String editData(Model model) {
        int record = datactrl.getDataCount();

        try {
            newdata = datactrl.findDataEntities().subList(0, record);
        } catch (Exception e) {
        }
        model.addAttribute("goData", newdata);
        model.addAttribute("record", record);
        return "editktp";
    }

    @GetMapping("/delete")
    public String deleteData(Integer id) throws NonexistentEntityException {
        datactrl.findData(id);
        datactrl.destroy(id);
        return "database";
    }

    @RequestMapping("/view")
    public String viewData(Model model) {
                int record = datactrl.getDataCount();

        try {
            newdata = datactrl.findDataEntities().subList(0, record);
        } catch (Exception e) {
        }
        model.addAttribute("goData", newdata);
        model.addAttribute("record", record);
        
        return "viewktp";
    }
}


package pae.jogja.project.ktp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import pae.jogja.project.ktp.exceptions.NonexistentEntityException;


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
    public String createData(Model model) {
        model.addAttribute("data",data);
        return "create";
    }

    @PostMapping(value = "/newdata", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public RedirectView newData(@RequestParam("foto")MultipartFile multipart, HttpServletRequest data) 
            throws ParseException, Exception {

        Data datadat = new Data();

        int iid = Integer.parseInt(data.getParameter("id"));
        String nonik = data.getParameter("noktp");
        String namaInput = data.getParameter("nama");
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(data.getParameter("tgllahir"));
        String jenis = data.getParameter("jeniskelamin");
        String alamatInput = data.getParameter("alamat");
        String agama = data.getParameter("agama");
        String status = data.getParameter("status");
        String pekerjaan = data.getParameter("pekerjaan");
        String warganegara = data.getParameter("warganegara");
        String berlaku = "Seumur Hidup";
        byte[] image = multipart.getBytes();
        
        datadat.setId(iid);
        datadat.setNoktp(nonik);
        datadat.setNama(namaInput);
        datadat.setTgllahir(date);
        datadat.setJeniskelamin(jenis);
        datadat.setAlamat(alamatInput);
        datadat.setAgama(agama);
        datadat.setStatus(status);
        datadat.setPekerjaan(pekerjaan);
        datadat.setWarganegara(warganegara);
        datadat.setBerlakuhingga(berlaku);
        datadat.setFoto(image);

        datactrl.create(datadat);

        return new RedirectView("/data");
    }
    
    @RequestMapping(value = "/gambar", method = RequestMethod.GET, produces = {
        MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE
    })
    public ResponseEntity<byte[]> getImg(@RequestParam("id") int id) throws Exception {
        data = datactrl.findData(id);
        byte[] image = data.getFoto();
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }

    @RequestMapping("/edit/{id}")
    public String editPage(@PathVariable(value = "id")int id, Model model) {
        
        data = datactrl.findData(id);
        model.addAttribute("goData", data);
        
        return "editktp";
    }

    @PostMapping(value = "/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public RedirectView editData(@RequestParam("foto") MultipartFile img, HttpServletRequest data) 
            throws ParseException, Exception {

        Data datadat = new Data();
        
        int iid = Integer.parseInt(data.getParameter("id"));
        String nonik = data.getParameter("noktp");
        String namaInput = data.getParameter("nama");
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(data.getParameter("tgllahir"));
        String jenis = data.getParameter("jeniskelamin");
        String alamatInput = data.getParameter("alamat");
        String agama = data.getParameter("agama");
        String status = data.getParameter("status");
        String pekerjaan = data.getParameter("pekerjaan");
        String warganegara = data.getParameter("warganegara");
        String berlaku = "Seumur Hidup";
        byte[] image = img.getBytes();
        
        datadat.setId(iid);
        datadat.setNoktp(nonik);
        datadat.setNama(namaInput);
        datadat.setTgllahir(date);
        datadat.setJeniskelamin(jenis);        
        datadat.setAlamat(alamatInput);
        datadat.setAgama(agama);
        datadat.setStatus(status);
        datadat.setPekerjaan(pekerjaan);
        datadat.setWarganegara(warganegara);
        datadat.setBerlakuhingga(berlaku);
        datadat.setFoto(image);

        datactrl.edit(datadat);

        return new RedirectView("/data");
    }

    @GetMapping("/delete/{id}")
    public String deleteData(@PathVariable(value = "id")int id) throws NonexistentEntityException {
        datactrl.destroy(id);
        return "redirect:/data";
    }

    @RequestMapping("/view/{id}")
    public String viewData(@PathVariable int id, Model model) throws NonexistentEntityException {

        try {
            data = datactrl.findData(id);
        } catch (Exception e) {
        }
        model.addAttribute("goData", data);

        return "viewktp";
    }
}

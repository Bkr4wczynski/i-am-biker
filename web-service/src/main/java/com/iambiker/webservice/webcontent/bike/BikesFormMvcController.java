package com.iambiker.webservice.webcontent.bike;

import com.iambiker.webservice.model.dto.personal.BikeDTO;
import com.iambiker.webservice.model.dto.personal.EngineDTO;
import com.iambiker.webservice.security.JwtWebUtil;
import com.iambiker.webservice.util.RedirectManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class BikesFormMvcController {
    private final BikesServiceConnectionService connectionService;
    private final JwtWebUtil jwtWebUtil;
    private final RedirectManager redirectManager;

    @GetMapping("/add-bike")
    public String showAddBikeForm(Model model) {
        BikeDTO bike = new BikeDTO();
        bike.setEngine(new EngineDTO());
        model.addAttribute("bike", bike);
        return "bike/addBike";
    }

    @GetMapping("/update-bike")
    public String showUpdateBikeForm(@RequestParam int id, Model model) {
        BikeDTO bike = connectionService.getBikeById(id);
        model.addAttribute("bike", bike);
        return "bike/updateBike";
    }

    @PostMapping("/add-bike")
    public String addBike(@CookieValue("token") String token, @ModelAttribute("bike") BikeDTO bike) {
        int userId = jwtWebUtil.getUserIdFromToken(token);
        bike.setUser_id(userId);
        connectionService.saveBike(bike);
        return redirectManager.redirect("/web/my-bikes");
    }

    @DeleteMapping("/delete-bike")
    public String deleteBike(@RequestParam int id) {
        connectionService.deleteBike(id);
        return redirectManager.redirect("/web/my-bikes");
    }

    @PutMapping("/update-bike")
    public String updateBike(@ModelAttribute("bike") BikeDTO bike) {
        connectionService.updateBike(bike);
        return redirectManager.redirect("/web/my-bike?id="+bike.getId());
    }

}

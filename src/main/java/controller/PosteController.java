package controller;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import model.Poste;
import model.TypePoste;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import service.PosteService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/poste")
public class PosteController {

    private final PosteService posteService;

    // Liste des postes
    @GetMapping
    public String listPostes(Model model) {
        List<Poste> postes = posteService.getAllPostes();
        model.addAttribute("postes", postes);
        return "poste/listPoste";
    }

    // Créer un nouveau poste
    @GetMapping("/new")
    public String addPoste(Model model) {
        model.addAttribute("poste", new Poste());
        model.addAttribute("types", TypePoste.values());
        return "poste/addPoste";
    }

    @PostMapping("/save")
    public String savePoste(@Valid @ModelAttribute("poste") Poste poste,
                            BindingResult bindingResult,
                            Model model,
                            RedirectAttributes ra)
    {
        model.addAttribute("types", TypePoste.values());

        // On teste si les données saisies dans le formulaire sont valides
        if (bindingResult.hasErrors())
        {
            return "poste/addPoste"; // Retourner à la vue avec les erreurs
        }

        try
        {
            posteService.addPoste(poste);
            // Message flash
            String msg = "Le poste " + poste.getLibellePoste() + " a été créé avec succès !";
            ra.addFlashAttribute("msg", msg);

            // Après la création d'un nouveau poste on se redirige vers la page des listes des postes
            return "redirect:/poste";

        } catch (IllegalArgumentException e)
        {
            bindingResult.rejectValue("libellePoste", null, e.getMessage());
            return "poste/addPoste";
        }

    }
}

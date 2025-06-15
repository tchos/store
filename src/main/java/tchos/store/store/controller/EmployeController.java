package tchos.store.store.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tchos.store.store.model.Employe;
import tchos.store.store.service.DepartementService;
import tchos.store.store.service.EmployeService;
import tchos.store.store.service.PosteService;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/employe")
public class EmployeController {

    private final EmployeService employeService;
    private final DepartementService departementService;
    private final PosteService posteService;

    // Liste de tous les employes
    @GetMapping
    public String employes(Model model)
    {
        model.addAttribute("employes", employeService.getAllEmployes());
        return "employe/list";
    }

    /* Créer un nouvel employe: la création et
    la modification d'une entité aura toujours 2 méthodes
    (une avec GetMapping (pour le formulaire ajout/modification et
    l'autre avec PostMapping pour traiter les donnees saisies
    via le formulaire */
    @GetMapping("/new")
    public String addEmploye(Model model)
    {
        model.addAttribute("employe", new Employe());
        model.addAttribute("postes", posteService.getAllPostes());
        model.addAttribute("departements", departementService.getAllDepartements());
        return "employe/add";
    }

    // Sauvegarde des donnees saisies du formulaire
    @PostMapping("/save")
    public String saveEmploye(@Valid @ModelAttribute("employe") Employe employe,
                              BindingResult bindingResult,
                              Model model,
                              RedirectAttributes ra)
    {
        model.addAttribute("departements", departementService.getAllDepartements());
        model.addAttribute("postes", posteService.getAllPostes());

        // On teste si les données saisies dans le formulaire sont valides
        if (bindingResult.hasErrors())
        {
            return "employe/add"; // Retourner à la vue avec les erreurs
        }

        try
        {
            employeService.addEmploye(employe);
            // Message flash
            String msg = "L'employé " + employe.getNom() + " a été créé avec succès !";
            ra.addFlashAttribute("msg", msg);

            /* Après la création d'un employe
            on se redirige vers la page des listes des employes */
            return "redirect:/employe";

        } catch (IllegalArgumentException e)
        {
            bindingResult.rejectValue("nom", null, e.getMessage());
            return "employe/add";
        }
    }

    // Afficher les détails d'un employé
    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable UUID id, Model model) {
        model.addAttribute("employe", employeService.getEmployeById(id));
        return "employe/detail";
    }

    /* Modification des infos d'employe: la création et
    la modification d'une entité aura toujours 2 méthodes
    (une avec GetMapping (pour le formulaire ajout/modification et
    l'autre avec PostMapping pour traiter les donnees saisies
    via le formulaire */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable UUID id, Model model, RedirectAttributes ra)
    {
        // On recupere l'employe a modifier
        model.addAttribute("employe", employeService.getEmployeById(id));
        model.addAttribute("postes", posteService.getAllPostes());
        model.addAttribute("departements", departementService.getAllDepartements());

        return "employe/edit";
    }

    // Sauvegarde des donnees saisies du formulaire
    @PostMapping("/update/{id}")
    public String update(@PathVariable UUID id,
                         @Valid @ModelAttribute("employe") Employe employe,
                         BindingResult bindingResult,
                         Model model,
                         RedirectAttributes ra)
    {
        // On teste si les données saisies dans le formulaire sont valides
        if (bindingResult.hasErrors())
        {
            // Retourner à la vue avec les erreurs
            return "employe/edit";
        }

        try
        {
            employeService.updateEmploye(id, employe);
            // Message flash
            String msg = "L'employé " + employe.getNom() + " a été modifié avec succès !";
            ra.addFlashAttribute("msg", msg);

            /* Après la modification d'un employe
            on se redirige vers la page des listes des employes */
            return "redirect:/employe";

        } catch (IllegalArgumentException e)
        {
            bindingResult.rejectValue("nom", null, e.getMessage());
            return "employe/add";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable UUID id, Model model, RedirectAttributes ra)
    {
        // Suppression de l'employe dont l'id est {id}
        employeService.deleteEmploye(id);

        // Message flash
        String msg = "L'employé a été supprimé avec succès !";
        ra.addFlashAttribute("msg", msg);

        // Redirection vers la page affichant les listes des employes
        return "redirect:/employe";
    }
}
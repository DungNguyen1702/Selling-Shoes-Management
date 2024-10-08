package group1.intern.controller;

import group1.intern.bean.AccountRegistration;
import group1.intern.bean.ToastMessage;
import group1.intern.model.Enum.AccountRole;
import group1.intern.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class RegisterController {

    @Autowired
    private AuthService authService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("accountRegistration", new AccountRegistration());
        return "screens/auth/register";
    }

    @PostMapping("/register")
    public String registerAccount(
        @Valid @ModelAttribute("accountRegistration") AccountRegistration accountRegistration,
        BindingResult bindingResult,
        RedirectAttributes redirectAttrs,
        Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "screens/auth/register";
        }

        try {
            authService.register(accountRegistration, AccountRole.CUSTOMER);
        } catch (Exception ex) {
            model.addAttribute("toastMessages", new ToastMessage("error", ex.getMessage()));
            return "screens/auth/register";
        }

        redirectAttrs.addFlashAttribute("toastMessages", new ToastMessage("success", "Đăng kí thành công"));
        return "redirect:/login";
    }
}
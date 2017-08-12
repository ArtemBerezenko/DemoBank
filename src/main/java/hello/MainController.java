package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import model.User;
import hello.UserRepository;

@Controller
@RequestMapping(path="/demo")
public class MainController {
    @Autowired
    private UserRepository userRepository;

    public @ResponseBody String addNewUser (@RequestParam String login,
                                            @RequestParam String password) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        userRepository.save(user);
        return "Saved";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}

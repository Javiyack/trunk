
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.RendezvousService;
import services.UserService;
import domain.Rendezvous;
import domain.User;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

	// Supporting services -----------------------------------------------------
	@Autowired
	UserService			userService;
	@Autowired
	ActorService		actorService;
	@Autowired
	RendezvousService	rendezvousService;


	// Constructors -----------------------------------------------------------

	public UserController() {
		super();
	}

	// list user ---------------------------------------------------------------		
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;

		final Collection<User> users = this.userService.findAll();

		result = new ModelAndView("user/list");
		result.addObject("users", users);
		result.addObject("requestUri", "user/list.do");
		result.addObject("showAnswer", false);

		return result;
	}

	// list attendants ---------------------------------------------------------------		
	@RequestMapping(value = "/listAttendants", method = RequestMethod.GET)
	public ModelAndView listAttendants(@RequestParam final int rendezvousId) {

		final ModelAndView result;

		final Collection<User> attendants = this.userService.findAttendantsByRendezvous(rendezvousId);

		result = new ModelAndView("user/list");
		result.addObject("users", attendants);
		result.addObject("requestUri", "user/list.do");
		result.addObject("showAnswer", true);
		result.addObject("rendezvousId", rendezvousId);

		return result;
	}
	// Create user ---------------------------------------------------------------	
	@RequestMapping("/create")
	public ModelAndView create() {
		ModelAndView result;
		final User user = this.userService.create();

		result = this.createEditModelAndView(user);

		return result;
	}

	// Create user ---------------------------------------------------------------	
	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final User user, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(user);
		else
			try {
				this.userService.save(user);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(user, "user.commit.error");
			}
		return result;
	}

	// Display user -----------------------------------------------------------
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int userId) {
		final ModelAndView result;
		final User user;
		final Collection<Rendezvous> rendezvouses;

		user = this.userService.findOne(userId);

		rendezvouses = this.rendezvousService.findReservedByUser(user.getId());

		result = new ModelAndView("user/display");

		result.addObject("user", user);
		result.addObject("rendezvouses", rendezvouses);

		return result;
	}

	// Auxiliary methods ---------------------------------------------------------------
	protected ModelAndView createEditModelAndView(final User user) {
		final ModelAndView result;
		result = this.createEditModelAndView(user, null);
		return result;
	}
	protected ModelAndView createEditModelAndView(final User user, final String message) {
		final ModelAndView result;

		result = new ModelAndView("user/create");
		result.addObject("user", user);
		result.addObject("message", message);

		return result;
	}

}

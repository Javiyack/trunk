/*
 * ProfileController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers.user;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.QuestionService;
import services.RendezvousService;
import services.UserService;
import controllers.AbstractController;
import domain.Question;
import domain.Rendezvous;
import domain.User;

@Controller
@RequestMapping("/question/user")
public class QuestionUserController extends AbstractController {

	// Services ---------------------------------------------------------------

	@Autowired
	private QuestionService		questionService;
	@Autowired
	private RendezvousService	rendezvousService;
	@Autowired
	private UserService			userService;


	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int rendezvousId) {
		ModelAndView result;
		Question question;

		question = this.questionService.create(rendezvousId);
		result = this.createEditModelAndView(question);

		return result;
	}

	// Edit ---------------------------------------------------------------
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int questionId) {
		ModelAndView result;
		Question question;
		question = this.questionService.findOne(questionId);

		result = new ModelAndView("question/user/edit");
		result.addObject("question", question);

		return result;
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping("/list")
	public ModelAndView list(@RequestParam final int rendezvousId) {
		ModelAndView result;
		Collection<Question> questions;

		final User user = this.userService.findByPrincipal();
		final Rendezvous rendezvous = this.rendezvousService.findOne(rendezvousId);
		Boolean showEdit = false;
		if (user.equals(rendezvous.getUser()) && !rendezvous.getDraft())
			showEdit = true;

		questions = this.questionService.findAllUnansweredByRendezvousId(rendezvousId);

		result = new ModelAndView("question/user/list");
		result.addObject("requestURI", "question/user/list.do");
		result.addObject("questions", questions);
		result.addObject("rendezvous", rendezvousId);
		result.addObject("showEdit", showEdit);

		return result;
	}

	// Save --------------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Question question, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(question);
		else
			try {
				this.questionService.save(question);
				result = new ModelAndView("redirect:/");
			} catch (final Throwable oops) {
				oops.printStackTrace();
				result = this.createEditModelAndView(question, "question.commit.error");
			}

		return result;
	}

	// Auxiliary methods ---------------------------------------------------------------
	protected ModelAndView createEditModelAndView(final Question question) {
		final ModelAndView result;
		result = this.createEditModelAndView(question, null);
		return result;
	}
	protected ModelAndView createEditModelAndView(final Question question, final String message) {
		final ModelAndView result;

		result = new ModelAndView("question/user/create");
		result.addObject("question", question);
		result.addObject("message", message);

		return result;
	}

}

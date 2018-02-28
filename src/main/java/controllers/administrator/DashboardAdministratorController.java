
package controllers.administrator;

import java.text.DecimalFormat;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdministratorService;
import utilities.Statistics;
import controllers.AbstractController;

@Controller
@RequestMapping("/dashboard")
public class DashboardAdministratorController extends AbstractController {

	@Autowired
	AdministratorService	administratorService;


	// Constructors -----------------------------------------------------------

	public DashboardAdministratorController() {
		super();
	}

		@RequestMapping(value = "/list", method = RequestMethod.GET)
		public ModelAndView display() {
			ModelAndView result;
			Statistics s;
			DecimalFormat df2 = new DecimalFormat("0.####");
			
	
			result = new ModelAndView("dashboard/display");
	
			final Object[] stats1 = this.administratorService.dashboardRendezvousesByUser();
			s = new Statistics(stats1);
			result.addObject("dashboard1", new String[]{df2.format(s.getMean()), df2.format(s.getStdDev())} );
	
			final Object[] stats2 = this.administratorService.dashboardRendezvousesRatioCreation();
			s = new Statistics(stats2);
			result.addObject("dashboard2", new String[]{df2.format(s.getMean()), df2.format(s.getStdDev())} );
	
			final Object[] stats3 = this.administratorService.dashboardUsersPerRendezvous();
			s = new Statistics(stats3);
			result.addObject("dashboard3", new String[]{df2.format(s.getMean()), df2.format(s.getStdDev())} );
	
			final Object[] stats4 = this.administratorService.dashboardRendezvousesRsvp();
			s = new Statistics(stats4);
			result.addObject("dashboard4", new String[]{df2.format(s.getMean()), df2.format(s.getStdDev())} );
	
			final Collection<Object> stats5 = this.administratorService.dashboardRendezvousesTop10();
			result.addObject("dashboard5", stats5);
	
			final Object[] stats6 = this.administratorService.dashboardAnnouncementsRatio();
			s = new Statistics(stats6);
			result.addObject("dashboard6", new String[]{df2.format(s.getMean()), df2.format(s.getStdDev())} );
	
			final Collection<Object> statistics7 = this.administratorService.dashboardAnnouncementsAbove75();
			result.addObject("dashboard7", statistics7);
	
			final Collection<Object> statistics8 = this.administratorService.dashboardRendezvousesLinked();
			result.addObject("dashboard8", statistics8);
	
			final Object[] stats9 = this.administratorService.dashboardQuestionsPerRendezvous();
			s = new Statistics(stats9);
			result.addObject("dashboard9", new String[]{df2.format(s.getMean()), df2.format(s.getStdDev())} );
	
			final Object[] stats10 = this.administratorService.dashboardAnswersPerRendezvous();
			s = new Statistics(stats10);
			result.addObject("dashboard10", new String[]{df2.format(s.getMean()), df2.format(s.getStdDev())} );
	
			final Object[] stats11 = this.administratorService.dashboardRepliesPerComment();
			s = new Statistics(stats11);
			result.addObject("dashboard11", new String[]{df2.format(s.getMean()), df2.format(s.getStdDev())} );
	
			return result;

	}

}

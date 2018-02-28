
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AnnouncementRepository;
import domain.Actor;
import domain.Administrator;
import domain.Announcement;
import domain.Rendezvous;
import domain.User;

@Service
@Transactional
public class AnnouncementService {

	// Managed repositories ------------------------------------------------
	@Autowired
	private AnnouncementRepository	announcementRepository;

	// Supporting services --------------------------------------------------
	@Autowired
	private RendezvousService		rendezvousService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private UserService				userService;


	// Constructor ----------------------------------------------------------
	public AnnouncementService() {
		super();
	}

	// Methods CRUD ---------------------------------------------------------
	public Announcement create(final int rendezvousId) {
		final Rendezvous rendezvous = this.rendezvousService.findOne(rendezvousId);
		final User user = this.userService.findByPrincipal();
		Assert.notNull(user);
		Assert.notNull(rendezvous);
		Assert.isTrue(rendezvous.getUser().equals(user));

		final Announcement result;

		result = new Announcement();
		result.setRendezvous(rendezvous);

		return result;
	}

	public Collection<Announcement> findAll() {

		Collection<Announcement> result;

		result = this.announcementRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Announcement findOne(final int announcementId) {
		Announcement result;

		result = this.announcementRepository.findOne(announcementId);
		Assert.notNull(result);

		return result;
	}

	public Announcement save(final Announcement announcement) {
		Assert.notNull(announcement);
		Announcement saved;

		if (announcement.getId() == 0) {
			final Date moment = new Date(System.currentTimeMillis() - 1);
			announcement.setMoment(moment);
		}

		saved = this.announcementRepository.save(announcement);

		return saved;
	}

	public void delete(final Announcement announcement) {
		Assert.notNull(announcement);

		this.announcementRepository.delete(announcement);
	}

	// Other business methods -------------------------------------------------

	public Collection<Announcement> findByRendezvous(final int rendezvousId) {
		final Rendezvous rendezvous = this.rendezvousService.findOne(rendezvousId);
		Assert.notNull(rendezvous);
		final Collection<Announcement> announcements = this.announcementRepository.findByRendezvous(rendezvousId);
		Assert.notNull(announcements);
		return announcements;
	}

	public void deleteInBatch(final Collection<Announcement> announcements) {
		// TOASK ¿habria que comprobar aqui tambien que en usuario logado es admin?

		Assert.notEmpty(announcements);

		this.announcementRepository.deleteInBatch(announcements);

	}

	public void deleteByAdministrator(final Announcement announcement) {
		// TODO Auto-generated method stub
		final Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor instanceof Administrator);

		this.announcementRepository.delete(announcement);
	}

}

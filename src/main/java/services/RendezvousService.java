/*
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.RendezvousRepository;
import domain.Actor;
import domain.Administrator;
import domain.Announcement;
import domain.Comment;
import domain.Coordinate;
import domain.Link;
import domain.Question;
import domain.Rendezvous;
import domain.Reservation;
import domain.User;

@Service
@Transactional
public class RendezvousService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RendezvousRepository	rendezvousRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private UserService				userService;
	@Autowired
	private ActorService			actorService;
	@Autowired
	private AdministratorService	adminService;
	@Autowired
	private ReservationService		reservationService;
	@Autowired
	private QuestionService			questionService;
	@Autowired
	private AnnouncementService		announcementService;
	@Autowired
	private LinkService				linkService;
	@Autowired
	private CommentService			commentService;


	// Constructors -----------------------------------------------------------

	public RendezvousService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Rendezvous create() {

		final User user;
		user = this.userService.findByPrincipal();
		Assert.notNull(user);

		final Rendezvous res;
		res = new Rendezvous();

		res.setUser(user);
		res.setDeleted(false);
		res.setDraft(true);
		res.setLocation(new Coordinate());

		return res;
	}

	public List<Rendezvous> findAll() {
		List<Rendezvous> result;

		Assert.notNull(this.rendezvousRepository);
		result = this.rendezvousRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public List<Rendezvous> findAllNotAdult() {
		List<Rendezvous> result;

		Assert.notNull(this.rendezvousRepository);
		result = this.rendezvousRepository.findAllNotAdult();
		Assert.notNull(result);

		return result;
	}

	public Rendezvous findOne(final int rendezvousId) {

		Rendezvous result;
		Assert.isTrue(rendezvousId > 0);

		result = this.rendezvousRepository.findOne(rendezvousId);

		return result;
	}

	// TODO ERN: añadido método findOneToEdit de Rendezvous
	public Rendezvous findOneToEdit(final int rendezvousId) {
		Rendezvous result;
		Assert.isTrue(rendezvousId != 0);
		result = this.rendezvousRepository.findOne(rendezvousId);
		this.checkPrincipal(result);
		Assert.isTrue(result.getDraft().equals(true));
		Assert.isTrue(result.getDeleted().equals(false), "Cannot edit a deleted Rendezvous");
		return result;
	}

	public Rendezvous save(final Rendezvous rendezvous) {
		assert rendezvous != null;

		final int id = rendezvous.getId();
		Rendezvous result;
		result = this.rendezvousRepository.save(rendezvous);

		if (id == 0) {

			Reservation reservation;
			reservation = this.reservationService.create();
			Assert.notNull(reservation);

			User user;
			user = this.userService.findByPrincipal();
			Assert.notNull(user);

			reservation.setUser(user);
			reservation.setRendezvous(result);

			this.reservationService.save(reservation);

		}

		return result;
	}

	public void delete(final Rendezvous rendezvous) {
		Assert.isTrue(rendezvous.getId() != 0);
		final Administrator admin = this.adminService.findByPrincipal();
		Assert.notNull(admin);
		this.rendezvousRepository.delete(rendezvous);
	}

	// Requisito funcional 6.2 ADMIN can remove a rendezvous that he or she thinks is inappropriate.
	// El borrado ha de hacerlo un admnistrador y es real, no virtual como el del usuario.
	public void remove(final Rendezvous rendezvous) {
		Assert.notNull(rendezvous);
		// Comprobamos que el actor autenticado es un "Administrator"

		final Actor actor = this.actorService.findByPrincipal();
		Assert.isTrue(actor instanceof Administrator);
		// Antes de borrar el rendezvous hay que borrar todos los objetos que lo referencien

		// Buscamos y borramos todas las reservas si las hubiera

		final Collection<Reservation> reservations = this.reservationService.findAllByRendezvousId(rendezvous.getId());
		if (!reservations.isEmpty())
			this.reservationService.deleteInBatch(reservations);

		// Buscamos y borramos todos las Questions si las hubiera

		final Collection<Question> questions = this.questionService.findAllByRendezvousId(rendezvous.getId());
		if (!questions.isEmpty())
			this.questionService.deleteInBatch(questions);

		// Buscamos y borramos todos los Announcements si los hubiera

		final Collection<Announcement> announcements = this.announcementService.findByRendezvous(rendezvous.getId());
		if (!announcements.isEmpty())
			this.announcementService.deleteInBatch(announcements);

		// Buscamos y borramos todos los Links si los hubiera

		final Collection<Link> links = this.linkService.findAllByRendezvousId(rendezvous.getId());
		if (!links.isEmpty())
			this.linkService.deleteInBatch(links);

		// buscamos y borramos todos los comments si los hubiera

		final Collection<Comment> comments = this.commentService.findAllByRendezvousId(rendezvous.getId());
		if (!comments.isEmpty())
			for (final Comment comment : comments)
				this.commentService.delete(comment);

		// Finalmente borramos el comentario

		this.rendezvousRepository.delete(rendezvous);

	}
	// Other business methods -------------------------------------------------

	public Collection<Rendezvous> findCreatedByUser(final int userId) {
		Collection<Rendezvous> result;
		result = this.rendezvousRepository.findCreatedByUserId(userId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Rendezvous> findReservedByUser(final int userId) {

		Collection<Rendezvous> result;
		result = this.rendezvousRepository.findReservedByUserId(userId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Rendezvous> findReservedAndNotCanceledByUserId(final int userId) {

		Collection<Rendezvous> result;
		result = this.rendezvousRepository.findReservedAndNotCanceledByUserId(userId);
		Assert.notNull(result);

		return result;

	}

	//Requisito 5.3
	public Rendezvous deleteByUser(final Rendezvous rendezvous) {
		Assert.isTrue(rendezvous.getId() != 0);
		this.checkPrincipal(rendezvous);
		Rendezvous result;
		Assert.isTrue(rendezvous.getDraft(), "Final mode is /True/");
		Assert.isTrue(!rendezvous.getDeleted(), "deleted value is /True/");
		rendezvous.setDeleted(true);
		result = this.rendezvousRepository.save(rendezvous);
		return result;
	}

	public Reservation reserveRendezvous(final Reservation reservation, final Rendezvous rendezvous) {

		Assert.notNull(reservation);
		Assert.notNull(rendezvous);

		final User principal = this.userService.findByPrincipal();
		Assert.notNull(principal);
		Assert.isTrue(!rendezvous.getUser().equals(principal), "Cannot reserve this rendezvous");
		Assert.isTrue(!this.findReservedByUser(principal.getId()).contains(rendezvous));
		if (rendezvous.getAdult().equals(true))
			Assert.isTrue(principal.getAdult().equals(true));

		reservation.setRendezvous(rendezvous);
		reservation.setUser(principal);
		reservation.setCanceled(false);

		return reservation;

	}

	public Reservation cancelRendezvous(final Reservation reservation) {
		final User principal = this.userService.findByPrincipal();
		Assert.notNull(principal);
		Assert.notNull(reservation);
		Assert.isTrue(this.findReservedByUser(principal.getId()).contains(reservation.getRendezvous()));
		Assert.isTrue(principal.equals(reservation.getUser()));
		reservation.setCanceled(true);
		return reservation;

	}

	// TODO ERN: añadido método checkPrincipal para checkear que el que modifica o
	// borra un Rendezvous es el que lo creó
	public void checkPrincipal(final Rendezvous rendezvous) {
		User creator;
		User principal;

		creator = rendezvous.getUser();
		principal = this.userService.findByPrincipal();

		Assert.isTrue(creator.equals(principal));
	}

	public Collection<Rendezvous> findSimilarRendezvous(final int rendezvousId) {
		final Rendezvous rendezvous = this.rendezvousRepository.findOne(rendezvousId);
		Assert.notNull(rendezvous);
		final Collection<Link> links = this.linkService.findAllByRendezvousId(rendezvousId);
		final Collection<Rendezvous> rendezvouses = new ArrayList<Rendezvous>();

		for (final Link l : links)
			if (l.getRendezvous().getId() != rendezvousId)
				rendezvouses.add(l.getRendezvous());
			else
				rendezvouses.add(l.getLinkedToRendezvous());

		return rendezvouses;
	}

	public Collection<Rendezvous> findCanceledByUserId(final int userId) {
		Collection<Rendezvous> result;
		result = this.rendezvousRepository.findCanceledByUserId(userId);
		Assert.notNull(result);

		return result;
	}

}

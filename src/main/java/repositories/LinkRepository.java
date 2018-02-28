/**
 * 
 */

package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Announcement;
import domain.Link;

@Repository
public interface LinkRepository extends JpaRepository<Link, Integer> {
	
	@Query("select l from Link l where l.rendezvous.id = ?1 or l.linkedToRendezvous.id = ?1")
	Collection<Link> findAllByRendezvousId(int id);	
}

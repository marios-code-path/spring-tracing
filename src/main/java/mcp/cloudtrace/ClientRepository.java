package mcp.cloudtrace;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

interface ClientRepository extends JpaRepository<Client, Long> {
    Collection<Client> findByClientId(String dn);
}

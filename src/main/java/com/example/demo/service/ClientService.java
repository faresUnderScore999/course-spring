package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.ClientDTO;
import com.example.demo.entity.Client;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.ClientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientService {

	private final ClientRepository clientRepository;

	@Transactional(readOnly = true)
	public List<ClientDTO> findAll() {
		return clientRepository.findAll().stream().map(ClientDTO::from).toList();
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		return ClientDTO.from(getEntity(id));
	}

	@Transactional(readOnly = true)
	public List<ClientDTO> findByEmail(String email) {
		return clientRepository.findByEmail(email).stream().map(ClientDTO::from).toList();
	}

	public ClientDTO create(ClientDTO dto) {
		Client client = Client.builder()
				.nom(dto.getNom())
				.prenom(dto.getPrenom())
				.email(dto.getEmail())
				.telephone(dto.getTelephone())
				.numPermis(dto.getNumPermis())
				.dateInscription(dto.getDateInscription())
				.build();
		return ClientDTO.from(clientRepository.save(client));
	}

	public ClientDTO update(Long id, ClientDTO dto) {
		Client client = getEntity(id);
		client.setNom(dto.getNom());
		client.setPrenom(dto.getPrenom());
		client.setEmail(dto.getEmail());
		client.setTelephone(dto.getTelephone());
		client.setNumPermis(dto.getNumPermis());
		client.setDateInscription(dto.getDateInscription());
		return ClientDTO.from(clientRepository.save(client));
	}

	public void delete(Long id) {
		clientRepository.delete(getEntity(id));
	}

	private Client getEntity(Long id) {
		return clientRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Client not found with id: " + id));
	}
}

package com.bridgelabz.fundoonotes.note.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.note.exceptions.ArchieveException;
import com.bridgelabz.fundoonotes.note.exceptions.EmptyNoteException;
import com.bridgelabz.fundoonotes.note.exceptions.InvalidDateException;
import com.bridgelabz.fundoonotes.note.exceptions.LabelException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteAuthorisationException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonotes.note.exceptions.NoteTrashException;
import com.bridgelabz.fundoonotes.note.exceptions.OwnerOfNoteNotFoundException;
import com.bridgelabz.fundoonotes.note.exceptions.PinException;
import com.bridgelabz.fundoonotes.note.models.Label;
import com.bridgelabz.fundoonotes.note.models.Note;
import com.bridgelabz.fundoonotes.note.models.NoteCreateDTO;
import com.bridgelabz.fundoonotes.note.models.NoteUpdateDTO;
import com.bridgelabz.fundoonotes.note.models.NoteViewDTO;
import com.bridgelabz.fundoonotes.note.repositories.LabelRepository;
import com.bridgelabz.fundoonotes.note.repositories.NoteRepository;
import com.bridgelabz.fundoonotes.note.utility.Utility;
import com.bridgelabz.fundoonotes.user.models.User;//using from user
import com.bridgelabz.fundoonotes.user.repositories.UserRepository;//using from user
import com.bridgelabz.fundoonotes.user.security.TokenProvider;//using from user

@Service
public class NoteServiceImpl implements NoteService {

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private UserRepository userRepsitory;

	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private LabelRepository labelRepository;

	@Override
	public NoteViewDTO createNote(String token, NoteCreateDTO noteCreateDTO)
			throws NoteException, EmptyNoteException, InvalidDateException {

		String userIdFromToken = tokenProvider.parseToken(token);

		Utility.validateNoteWhileCreating(noteCreateDTO);

		Note note = new Note();
		note.setTitle(noteCreateDTO.getTitle());
		note.setDescription(noteCreateDTO.getDescription());
		if (noteCreateDTO.getReminder() != null) {
			if (noteCreateDTO.getReminder().before(new Date())) {
				throw new InvalidDateException("reminder should not be earlier from now");
			}
		}
		note.setReminder(noteCreateDTO.getReminder());

		if (noteCreateDTO.getColor() != null)
			note.setColor(noteCreateDTO.getColor());
		else
			note.setColor("white");

		if (noteCreateDTO.isArchived()) {
			note.setArchived(true);
		}

		if (noteCreateDTO.isPinned()) {
			note.setPinned(true);
		}

		if (!noteCreateDTO.getLabelList().isEmpty()) {

			List<String> labelNames = noteCreateDTO.getLabelList();

			List<Label> list = new ArrayList<Label>();

			for (int i = 0; i < labelNames.size(); i++) {
				Label labelOfUser = labelRepository.findByUserIdAndName(userIdFromToken, labelNames.get(i));

				if (labelOfUser == null) {
					Label label = new Label();
					label.setUserId(userIdFromToken);
					label.setName(labelNames.get(i));

					labelRepository.insert(label);

					list.add(label);
					note.setLabelList(list);
				} else {
					list.add(labelOfUser);
					note.setLabelList(list);
				}
			}
		}

		note.setCreatedAt(new Date());

		note.setUserId(userIdFromToken);

		noteRepository.insert(note);

		NoteViewDTO noteViewDTO = modelMapper.map(note, NoteViewDTO.class);
		return noteViewDTO;
	}

	@Override
	public void updateNote(String token, NoteUpdateDTO noteUpdateDTO)
			throws NoteException, OwnerOfNoteNotFoundException, NoteNotFoundException, NoteAuthorisationException {

		Note note = validate(token, noteUpdateDTO.getId());
		if (noteUpdateDTO.getTitle() != null) {
			note.setTitle(noteUpdateDTO.getTitle());
		}
		if (noteUpdateDTO.getDescription() != null) {
			note.setDescription(noteUpdateDTO.getDescription());
		}
		note.setUpdatedAt(new Date());

		noteRepository.save(note);
	}

	@Override
	public List<Note> getAllNotes(String token) {

		String userIdFromToken = tokenProvider.parseToken(token);
		List<Note> noteList = noteRepository.findAllByuserId(userIdFromToken);

		return noteList;
	}

	@Override
	public void trashNote(String token, String noteId)
			throws OwnerOfNoteNotFoundException, NoteException, NoteNotFoundException, NoteAuthorisationException {

		Note note = validate(token, noteId);

		note.setTrashed(true);

		noteRepository.save(note);
	}

	@Override
	public void permanentlyDeleteNote(String token, String id, boolean isDelete) throws NoteException,
			OwnerOfNoteNotFoundException, NoteNotFoundException, NoteAuthorisationException, NoteTrashException {

		Note note = validate(token, id);

		if (!note.isTrashed()) {
			throw new NoteTrashException("Note not trashed yet!");
		}

		if (isDelete) {
			noteRepository.deleteById(note.getId());
		} else {
			note.setTrashed(false);
			noteRepository.save(note);
		}
	}

	private Note validate(String token, String noteId)
			throws NoteException, OwnerOfNoteNotFoundException, NoteNotFoundException, NoteAuthorisationException {

		if (noteId == null) {
			throw new NoteException("For deletion of note \"id\" is needed");
		}

		String userIdFromToken = tokenProvider.parseToken(token);
		Optional<User> optionalUser = userRepsitory.findById(userIdFromToken);
		if (!optionalUser.isPresent()) {
			throw new OwnerOfNoteNotFoundException("Note owner not present");
		}
		Optional<Note> optionalnote = noteRepository.findById(noteId);
		if (!optionalnote.isPresent()) {
			throw new NoteNotFoundException("given note-id to delete is not present");
		}

		if (!userIdFromToken.equals(optionalnote.get().getUserId())) {
			throw new NoteAuthorisationException("Unauthorised access of note to delete");
		}
		return optionalnote.get();
	}

	@Override
	public void addReminder(String token, String noteId, String remindDate) throws ParseException, InvalidDateException,
			NoteException, OwnerOfNoteNotFoundException, NoteNotFoundException, NoteAuthorisationException {
		Date remindDate1 = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").parse(remindDate);

		Note note = validate(token, noteId);

		if (remindDate1.before(new Date())) {
			throw new InvalidDateException("reminder should not be earlier from now");
		}
		note.setReminder(remindDate1);

		noteRepository.save(note);
	}

	@Override
	public void removeReminder(String token, String noteId)
			throws NoteException, OwnerOfNoteNotFoundException, NoteNotFoundException, NoteAuthorisationException {

		Note note = validate(token, noteId);

		note.setReminder(null);

		noteRepository.save(note);
	}

	@Override
	public void addPin(String token, String noteId) throws NoteException, OwnerOfNoteNotFoundException,
			NoteNotFoundException, NoteAuthorisationException, PinException {

		Note note = validate(token, noteId);

		if (note.isPinned()) {
			throw new PinException("The note is already pinned");
		}
		if (note.isArchived()) {
			note.setArchived(false);
		}
		note.setPinned(true);
	}

	@Override
	public void removePin(String token, String noteId) throws NoteException, OwnerOfNoteNotFoundException,
			NoteNotFoundException, NoteAuthorisationException, PinException {

		Note note = validate(token, noteId);
		if (!note.isPinned()) {
			throw new PinException("The note is already unPinned");
		}
		note.setPinned(false);
	}

	@Override
	public void addToArchive(String token, String noteId) throws NoteException, OwnerOfNoteNotFoundException,
			NoteNotFoundException, NoteAuthorisationException, ArchieveException {

		Note note = validate(token, noteId);

		if (note.isArchived()) {
			throw new ArchieveException("The note is already added to archieve");
		}
		note.setArchived(true);
	}

	@Override
	public void removeFromArchive(String token, String noteId) throws NoteException, OwnerOfNoteNotFoundException,
			NoteNotFoundException, NoteAuthorisationException, ArchieveException {

		Note note = validate(token, noteId);

		if (!note.isArchived()) {
			throw new ArchieveException("The note is not present in archieve");
		}
		note.setArchived(false);
	}

	@Override
	public void createLabel(String token, String labelName) throws LabelException {

		String userIdFromToken = tokenProvider.parseToken(token);
		System.out.println(labelRepository.findByUserIdAndName(userIdFromToken, labelName));
		if (labelRepository.findByUserIdAndName(userIdFromToken, labelName) != null) {
			throw new LabelException("label name already present");
		}

		Label label = new Label();
		label.setUserId(userIdFromToken);
		label.setName(labelName);
		labelRepository.insert(label);

	}

	@Override
	public void addLabel(String token, String noteId, List<String> labelNames)
			throws OwnerOfNoteNotFoundException, NoteNotFoundException {

		String userIdFromToken = tokenProvider.parseToken(token);
		Optional<User> optionalUser = userRepsitory.findById(userIdFromToken);
		if (!optionalUser.isPresent()) {
			throw new OwnerOfNoteNotFoundException("Note owner not present");
		}

		Optional<Note> optionalNote = noteRepository.findById(noteId);
		if (!optionalNote.isPresent()) {
			throw new NoteNotFoundException("given note-id to which label had to add is not present");
		}

		for (int i = 0; i < labelNames.size(); i++) {
			Label labelOfUser = labelRepository.findByUserIdAndName(userIdFromToken, labelNames.get(i));

			if (labelOfUser == null) {
				Label label = new Label();
				label.setUserId(userIdFromToken);
				label.setName(labelNames.get(i));

				labelRepository.insert(label);

				List<Label> labelListInNote = optionalNote.get().getLabelList();
				if (labelListInNote != null) {
					optionalNote.get().getLabelList().add(label);
				} else {
					List<Label> list = new ArrayList<Label>();
					list.add(label);
					optionalNote.get().setLabelList(list);
				}

				noteRepository.save(optionalNote.get());
			} else {
				List<Label> labelListInNote = optionalNote.get().getLabelList();

				List<String> labelStringNamesInNote;
				if (labelListInNote != null) {
					labelStringNamesInNote = new ArrayList<String>();
					for (int k = 0; k < labelListInNote.size(); k++) {
						labelStringNamesInNote.add(labelListInNote.get(k).getName());
					}
					if (!labelStringNamesInNote.contains(labelOfUser.getName())) {
						optionalNote.get().getLabelList().add(labelOfUser);
					}
				} else {
					List<Label> list = new ArrayList<Label>();
					list.add(labelOfUser);
					optionalNote.get().setLabelList(list);
				}
				noteRepository.save(optionalNote.get());
			}
		}
	}

	@Override
	public void editLabel(String token, String currentLabelName, String newLabelName)
			throws OwnerOfNoteNotFoundException, LabelException {

		String userIdFromToken = tokenProvider.parseToken(token);
		Optional<User> optionalUser = userRepsitory.findById(userIdFromToken);
		if (!optionalUser.isPresent()) {
			throw new OwnerOfNoteNotFoundException("Note owner not present");
		}

		Label labelOfUser = labelRepository.findByUserIdAndName(userIdFromToken, currentLabelName);
		if (labelOfUser == null) {
			throw new LabelException("given label not found");
		}

		List<Note> listOfNotes = new ArrayList<>();
		listOfNotes =  noteRepository.findAllByuserId(userIdFromToken);

		for (int i = 0; i < listOfNotes.size(); i++) {
			if (listOfNotes.get(i).getLabelList() != null) {
				for (int j = 0; j < listOfNotes.get(i).getLabelList().size(); j++) {
					if (listOfNotes.get(i).getLabelList().get(j).getName().equals(currentLabelName)) {

						listOfNotes.get(i).getLabelList().get(j).setName(newLabelName);

						Note note = listOfNotes.get(i);

						noteRepository.save(note);
					}
				}
			}
		}
		labelOfUser.setName(newLabelName);
		labelRepository.save(labelOfUser);
	}

	@Override
	public void deleteLabel(String token, String labelName) throws OwnerOfNoteNotFoundException, LabelException {
		
		String userIdFromToken = tokenProvider.parseToken(token);
		Optional<User> optionalUser = userRepsitory.findById(userIdFromToken);
		if (!optionalUser.isPresent()) {
			throw new OwnerOfNoteNotFoundException("Note owner not present");
		}

		Label labelOfUser = labelRepository.findByUserIdAndName(userIdFromToken, labelName);
		if (labelOfUser == null) {
			throw new LabelException("given label not found");
		}
		
		List<Note> listOfNotes = new ArrayList<>();
		listOfNotes = noteRepository.findAllByuserId(userIdFromToken);
		
		for (int i = 0; i < listOfNotes.size(); i++) {
			if (listOfNotes.get(i).getLabelList() != null) {
				for (int j = 0; j < listOfNotes.get(i).getLabelList().size(); j++) {
					if (listOfNotes.get(i).getLabelList().get(j).getName().equals(labelName)) {


						listOfNotes.get(i).getLabelList().remove(j);

						Note note = listOfNotes.get(i);

						noteRepository.save(note);
					}
				}
			}
		}
		labelRepository.delete(labelOfUser);

	}
	

}

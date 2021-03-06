package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.module.exceptions.DuplicatePersonException;
import seedu.address.model.module.exceptions.PersonNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class UniqueModuleListTest {

    private final UniqueModuleList uniqueModuleList = new UniqueModuleList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniqueModuleList.contains(ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniqueModuleList.add(ALICE);
        assertTrue(uniqueModuleList.contains(ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniqueModuleList.add(ALICE);
        Module editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
            .build();
        assertTrue(uniqueModuleList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniqueModuleList.add(ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniqueModuleList.add(ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList.setPerson(null, ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList.setPerson(ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueModuleList.setPerson(ALICE, ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniqueModuleList.add(ALICE);
        uniqueModuleList.setPerson(ALICE, ALICE);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        expectedUniqueModuleList.add(ALICE);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniqueModuleList.add(ALICE);
        Module editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
            .build();
        uniqueModuleList.setPerson(ALICE, editedAlice);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        expectedUniqueModuleList.add(editedAlice);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniqueModuleList.add(ALICE);
        uniqueModuleList.setPerson(ALICE, BOB);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        expectedUniqueModuleList.add(BOB);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniqueModuleList.add(ALICE);
        uniqueModuleList.add(BOB);
        assertThrows(DuplicatePersonException.class, () -> uniqueModuleList.setPerson(ALICE, BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(PersonNotFoundException.class, () -> uniqueModuleList.remove(ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniqueModuleList.add(ALICE);
        uniqueModuleList.remove(ALICE);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList.setPersons((UniqueModuleList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniqueModuleList.add(ALICE);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        expectedUniqueModuleList.add(BOB);
        uniqueModuleList.setPersons(expectedUniqueModuleList);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueModuleList.setPersons((List<Module>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniqueModuleList.add(ALICE);
        List<Module> moduleList = Collections.singletonList(BOB);
        uniqueModuleList.setPersons(moduleList);
        UniqueModuleList expectedUniqueModuleList = new UniqueModuleList();
        expectedUniqueModuleList.add(BOB);
        assertEquals(expectedUniqueModuleList, uniqueModuleList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Module> listWithDuplicateModules = Arrays.asList(ALICE, ALICE);
        assertThrows(DuplicatePersonException.class, () -> uniqueModuleList.setPersons(listWithDuplicateModules));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueModuleList.asUnmodifiableObservableList().remove(0));
    }
}

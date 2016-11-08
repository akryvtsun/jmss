package com.jmss.application.models;

import java.util.List;

import com.jmss.domain.Member;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;

public class MembersViewModel {
    private /*final*/ ObjectProperty<SingleSelectionModel<Tab>> selectionModel;

    private final StringProperty firstName = new SimpleStringProperty();
    private final StringProperty lastName = new SimpleStringProperty();
    private final ObservableList<Member> membersList;

    public MembersViewModel(List<Member> members) {
        membersList = FXCollections.observableList(members);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public ObservableList<Member> membersProperty() {
        return membersList;
    }
}

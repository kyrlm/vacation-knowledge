package gr.knowledge.internship.vacation.enums;

public enum VacationStatus {
    Approved("approved"),
    Pending("Pending"),
    Rejected("rejected");

    public final String status;

    VacationStatus(String status) {
        this.status = status;
    }
}

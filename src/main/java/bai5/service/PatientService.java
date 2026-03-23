package bai5.service;

import bai5.dao.PatientDAO;
import bai5.model.Patient;

import java.util.List;

public class PatientService {

    private PatientDAO dao = new PatientDAO();

    public List<Patient> getAll() {
        return dao.getAll();
    }

    public void add(Patient p) {
        dao.insert(p);
    }

    public boolean updateDisease(int id, String disease) {
        return dao.updateDisease(id, disease);
    }

    public double discharge(int id) {
        int days = dao.getDays(id);

        if (days == -1) return -1;

        double fee = dao.calculateFee(days);
        dao.delete(id);

        return fee;
    }
}
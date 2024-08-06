CREATE TABLE consultas (
   id INTEGER PRIMARY KEY AUTOINCREMENT,
   medico_id INTEGER NOT NULL,
   paciente_id INTEGER NOT NULL,
   data TIMESTAMP NOT NULL,
   FOREIGN KEY (medico_id) REFERENCES medicos(id),
   FOREIGN KEY (paciente_id) REFERENCES pacientes(id)
);
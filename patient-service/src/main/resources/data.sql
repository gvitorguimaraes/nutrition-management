
CREATE TABLE IF NOT EXISTS address (
    id UUID PRIMARY KEY,
    street VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(50) NOT NULL,
    zip_code VARCHAR(20) NOT NULL,
    country VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS patient (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    address_id UUID,
    date_of_birth DATE NOT NULL,
    registered_date DATE NOT NULL,
    CONSTRAINT fk_patient_address FOREIGN KEY (address_id) REFERENCES address(id)
);

INSERT INTO address (id, street, city, state, zip_code, country)
VALUES
 ('dd9d8af9-13ed-469d-a138-9ead26c92b2b', 'Rua das Flores, 123', 'São Paulo', 'SP', '01000-000', 'Brasil'),
 ('0685d5e5-635d-47f0-b061-afdfe7967efc', 'Av. Brasil, 456', 'Rio de Janeiro', 'RJ', '20000-000', 'Brasil'),
 ('f884655c-8ae8-4235-97c7-66a98caa8729', 'Rua Minas Gerais, 789', 'Belo Horizonte', 'MG', '30000-000', 'Brasil');

INSERT INTO patient (id, name, email, address_id, date_of_birth, registered_date)
VALUES
 ('997b0cc6-7d87-46aa-b670-aec2033487b6', 'João Silva', 'joao.silva@example.com', 'dd9d8af9-13ed-469d-a138-9ead26c92b2b', '1985-05-10', '2023-01-15'),
 ('f8795a0b-1718-41ea-bfe4-8ccaf078920c', 'Maria Oliveira', 'maria.oliveira@example.com', '0685d5e5-635d-47f0-b061-afdfe7967efc', '1990-08-20', '2023-02-01'),
 ('fde94279-3ccd-4eb8-a6d6-7819a8936333', 'Carlos Souza', 'carlos.souza@example.com', 'f884655c-8ae8-4235-97c7-66a98caa8729', '1978-12-05', '2023-03-10');

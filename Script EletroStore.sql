CREATE DATABASE eletro_store;

USE eletro_store;

## USUÁRIO E SENHA: 
## E-MAIL: teste@teste.com   SENHA: 123456
insert into cliente (id, cpf, email, nome, senha) values ('35', '12345678980', 'teste@teste.com', 'Fulano da Silva', '$2a$10$cGQU1bH2uwIGxC/5LzsV4.I9lAGZl0nw4/.xL9oPtgQn2k/CqMWG2');

insert into endereco values ('57', 'Vila Guarani', '04313210', 'São Paulo', 'São Paulo', 'Rua João', '158', '35');

insert into pagamento (id, cvv, expiracao, nome, numero, cliente_id)
values ('59', '508', '10/26', 'Fulano da Silva', '5207 5500 2860 0348', '35');

INSERT INTO produtos values('1', 'Notebook', 'Notebook Lenovo Ultrafino Ideapad 3i Intel Core i3-10110U 4GB 1TB W10 15.6" Prata', 'Lenovo', 'Notebook Ultrafino Ideapad', '13171321600_1SZ.jpg', '50', '3800');
INSERT INTO produtos values('2', 'Celular', 'Smartphone Samsung Galaxy S21 Ultra 256GB 5G Wi-Fi Tela 6.8'' Dual Chip 12GB RAM Câmera Quádrupla + Selfie 40MP - Preto', 'Samsung', 'Galaxy S21', '22810434791_1SZ.jpg', '100', '6000');
INSERT INTO produtos values('3', 'TV', 'Smart Tv 43" UHD Samsung 4k 43AU7700 Processador Crystal 4k Tela Sem Limites Alexa Built In Controle Único', 'Samsung', 'Smart TV 43" UHD', '33068549711_1SZ.jpg', '500', '2400');
INSERT INTO produtos values('4', 'Notebook', 'Notebook Acer Aspire 5 A515-54-50BT Intel Core i5-10210U 8GB 512GB W10 15,6" Prata', 'Acer', 'Notebook Acer Aspire 5', '43273554842_1SZ.jpg', '500', '3700');
INSERT INTO produtos values('5', 'Celular', 'Smartphone Motorola Moto E7 Power 32GB 4G Wi-Fi Tela 6.5'' Dual Chip 2GB RAM Câmera Dupla + Selfie 5MP - Azul Metálico', 'Motorola', 'Motorola Moto E7', '52910438853_1SZ.jpg', '200', '700');


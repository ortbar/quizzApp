




-- Preguntas originales (1-10)
INSERT INTO questions (texto_pregunta) VALUES
('¿Cómo se llama la escuela de magia a la que asiste Harry Potter?'),
('¿Cuál es el nombre completo de Dumbledore?'),
('¿Qué criatura guardaba la Cámara de los Secretos?'),
('¿Quién es el padrino de Harry Potter?'),
('¿Qué objeto representa a la Casa Gryffindor?'),
('¿Qué poción hace que quien la beba diga solo la verdad?'),
('¿Quién mató a Dumbledore?'),
('¿Qué criatura se convierte en el peor miedo de quien la ve?'),
('¿Qué posición juega Harry en el equipo de Quidditch?'),
('¿Qué Horrocrux destruyó Ron Weasley?');

-- Respuestas preguntas 1-10
INSERT INTO answers (answer_text, es_correcta, question_id) VALUES
('Hogwarts', true, 1),
('Durmstrang', false, 1),
('Beauxbatons', false, 1),
('Albus Percival Wulfric Brian Dumbledore', true, 2),
('Albus Severus Potter', false, 2),
('Gellert Grindelwald', false, 2),
('Un basilisco', true, 3),
('Un dragón', false, 3),
('Un Dementor', false, 3),
('Sirius Black', true, 4),
('Remus Lupin', false, 4),
('James Potter', false, 4),
('Un león', true, 5),
('Un cuervo', false, 5),
('Una serpiente', false, 5),
('Veritaserum', true, 6),
('Amortentia', false, 6),
('Felix Felicis', false, 6),
('Severus Snape', true, 7),
('Bellatrix Lestrange', false, 7),
('Lord Voldemort', false, 7),
('Un Boggart', true, 8),
('Un Inferius', false, 8),
('Un Hipogrifo', false, 8),
('Buscador', true, 9),
('Cazador', false, 9),
('Golpeador', false, 9),
('El relicario de Slytherin', true, 10),
('La copa de Hufflepuff', false, 10),
('El diario de Tom Riddle', false, 10);

-- Nuevas preguntas (11-20)
INSERT INTO questions (texto_pregunta) VALUES
('¿Qué animal es el Patronus de Harry Potter?'),
('¿Cuál es el ingrediente principal de la Poción Multijugos?'),
('¿Qué objeto permite la comunicación entre Harry y Sirius Black?'),
('¿Qué hechizo usa Hermione para crear fuego mágico?'),
('¿Quién es el guardián de las llaves en Hogwarts?'),
('¿Qué tipo de criatura es Fawkes?'),
('¿Qué asignatura enseña la profesora Sprout?'),
('¿Qué objeto mágico permite viajar en el tiempo?'),
('¿Qué familia es conocida por su amor por las artes oscuras?'),
('¿Qué hechizo protege contra los Dementores?');

-- Respuestas preguntas 11-20
INSERT INTO answers (answer_text, es_correcta, question_id) VALUES
('Ciervo', true, 11),
('Fénix', false, 11),
('Lobo', false, 11),
('Pestaña de la persona a imitar', false, 12),
('Pelo de la persona a imitar', true, 12),
('Sangre de la persona a imitar', false, 12),
('El mapa del merodeador', false, 13),
('El espejo de dos vías', true, 13),
('El galeón encantado', false, 13),
('Incendio', false, 14),
('Lumos', false, 14),
('Lacarnum Inflamarae', true, 14),
('Argus Filch', false, 15),
('Rubeus Hagrid', true, 15),
('Albus Dumbledore', false, 15),
('Hipogrifo', false, 16),
('Fénix', true, 16),
('Dragón', false, 16),
('Defensa contra las Artes Oscuras', false, 17),
('Herbología', true, 17),
('Adivinación', false, 17),
('Pensadero', false, 18),
('Giratiempo', true, 18),
('Varita de Saúco', false, 18),
('Los Weasley', false, 19),
('Los Malfoy', true, 19),
('Los Longbottom', false, 19),
('Expecto Patronum', true, 20),
('Protego', false, 20),
('Expelliarmus', false, 20);

-- Pregunta 21
INSERT INTO questions (texto_pregunta) VALUES ('¿Qué hechizo usó Harry para limpiar el lodo en el baño de Myrtle?');
INSERT INTO answers (answer_text, es_correcta, question_id) VALUES
('Scourgify', true, 21),
('Tergeo', false, 21),
('Evanesco', false, 21);

-- Pregunta 22
INSERT INTO questions (texto_pregunta) VALUES ('¿Cuál es el nombre del barco de Durmstrang?');
INSERT INTO answers (answer_text, es_correcta, question_id) VALUES
('El Gran Pez', false, 22),
('El Buceador de las Profundidades', false, 22),
('El Sirène', true, 22);

-- Pregunta 23
INSERT INTO questions (texto_pregunta) VALUES ('¿Qué objeto usó Barty Crouch Jr. para mantener a Moody prisionero?');
INSERT INTO answers (answer_text, es_correcta, question_id) VALUES
('Un baúl de siete llaves', false, 23),
('Un arcón mágico', false, 23),
('Un cofre encantado', true, 23);

-- Pregunta 24
INSERT INTO questions (texto_pregunta) VALUES ('¿Qué asignatura enseñaba la profesora Vector?');
INSERT INTO answers (answer_text, es_correcta, question_id) VALUES
('Aritmancia', true, 24),
('Runas Antiguas', false, 24),
('Astronomía', false, 24);

-- Pregunta 25
INSERT INTO questions (texto_pregunta) VALUES ('¿Qué criatura custodiaba el diario de Tom Riddle en la Cámara de los Secretos?');
INSERT INTO answers (answer_text, es_correcta, question_id) VALUES
('Aragog', false, 25),
('El basilisco', true, 25),
('Un troll', false, 25);

-- Pregunta 26
INSERT INTO questions (texto_pregunta) VALUES ('¿Cuál era el nombre completo de la madre de Harry?');
INSERT INTO answers (answer_text, es_correcta, question_id) VALUES
('Lily Luna Potter', false, 26),
('Lily Evans Potter', true, 26),
('Lily Jane Potter', false, 26);

-- Pregunta 27
INSERT INTO questions (texto_pregunta) VALUES ('¿Qué hechizo usó Hermione para ocultar el campamento durante la búsqueda de Horrocruxes?');
INSERT INTO answers (answer_text, es_correcta, question_id) VALUES
('Protego Totalum', false, 27),
('Salvio Hexia', false, 27),
('Cave Inimicum', true, 27);

-- Pregunta 28
INSERT INTO questions (texto_pregunta) VALUES ('¿Qué objeto usaban los mortífagos para comunicarse durante la Copa Mundial?');
INSERT INTO answers (answer_text, es_correcta, question_id) VALUES
('Monedas encantadas', false, 28),
('La Marca Tenebrosa', true, 28),
('Espejos de dos vías', false, 28);

-- Pregunta 29
INSERT INTO questions (texto_pregunta) VALUES ('¿Qué forma toma el Patronus de Kingsley Shacklebolt?');
INSERT INTO answers (answer_text, es_correcta, question_id) VALUES
('Lince', true, 29),
('Jabalí', false, 29),
('Águila', false, 29);

-- Pregunta 30
INSERT INTO questions (texto_pregunta) VALUES ('¿Qué tipo de varita tenía Draco Malfoy?');
INSERT INTO answers (answer_text, es_correcta, question_id) VALUES
('Sauce y pelo de unicornio', false, 30),
('Espino y pelo de unicornio', true, 30),
('Fresno y fibra de corazón de dragón', false, 30);

-- Pregunta 31
INSERT INTO questions (texto_pregunta) VALUES ('¿Qué hechizo inventó el Príncipe Mestizo para cortar?');
INSERT INTO answers (answer_text, es_correcta, question_id) VALUES
('Diffindo', false, 31),
('Sectumsempra', true, 31),
('Defodio', false, 31);

-- Pregunta 32
INSERT INTO questions (texto_pregunta) VALUES ('¿Qué criatura es Nagini?');
INSERT INTO answers (answer_text, es_correcta, question_id) VALUES
('Una serpiente mágica', false, 32),
('Un Horrocrux', false, 32),
('Ambas respuestas son correctas', true, 32);

-- Pregunta 33
INSERT INTO questions (texto_pregunta) VALUES ('¿Qué objeto usó Dumbledore para beber la poción en la cueva?');
INSERT INTO answers (answer_text, es_correcta, question_id) VALUES
('Una copa de oro', false, 33),
('Una concha marina', true, 33),
('Un cuenco de plata', false, 33);

-- Pregunta 34
INSERT INTO questions (texto_pregunta) VALUES ('¿Qué asignatura enseñaba el profesor Binns?');
INSERT INTO answers (answer_text, es_correcta, question_id) VALUES
('Historia de la Magia', true, 34),
('Estudios Muggles', false, 34),
('Adivinación', false, 34);

-- Pregunta 35
INSERT INTO questions (texto_pregunta) VALUES ('¿Qué hechizo usó Hermione para preparar su bolso para el viaje?');
INSERT INTO answers (answer_text, es_correcta, question_id) VALUES
('Capacious Extremis', true, 35),
('Undetectable Extension Charm', false, 35),
('Engorgio', false, 35);

-- Pregunta 36
INSERT INTO questions (texto_pregunta) VALUES ('¿Qué tipo de sangre tiene Voldemort?');
INSERT INTO answers (answer_text, es_correcta, question_id) VALUES
('Sangre pura', false, 36),
('Sangre mestiza', true, 36),
('Sangre muggle', false, 36);

-- Pregunta 37
INSERT INTO questions (texto_pregunta) VALUES ('¿Qué objeto usó Dumbledore para encontrar Horrocruxes?');
INSERT INTO answers (answer_text, es_correcta, question_id) VALUES
('El Pensadero', false, 37),
('El Deluminador', false, 37),
('Ningún objeto especial', true, 37);

-- Pregunta 38
INSERT INTO questions (texto_pregunta) VALUES ('¿Qué forma toma el Patronus de Arthur Weasley?');
INSERT INTO answers (answer_text, es_correcta, question_id) VALUES
('Comadreja', false, 38),
('Hurón', false, 38),
('No se ha revelado', true, 38);

-- Pregunta 39
INSERT INTO questions (texto_pregunta) VALUES ('¿Qué criatura es Witherwings?');
INSERT INTO answers (answer_text, es_correcta, question_id) VALUES
('El hipogrifo Buckbeak con otro nombre', true, 39),
('Un thestral especial', false, 39),
('Un dragón herido', false, 39);

-- Pregunta 40
INSERT INTO questions (texto_pregunta) VALUES ('¿Qué hechizo usó Harry para abrir el colgante de Slytherin?');
INSERT INTO answers (answer_text, es_correcta, question_id) VALUES
('Alohomora', false, 40),
('Parseltongue', false, 40),
('No usó hechizo, habló pársel', true, 40);
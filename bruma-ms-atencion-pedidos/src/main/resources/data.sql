INSERT INTO empleados (nombre, apellido_paterno, apellido_materno, dni, email, cargo, fecha_ingreso, estado) VALUES
                                                                                                                 ('Carlos', 'Ramirez', 'Perez', '71234568', 'carlos.ramirez@bruma.pe', 'Mozo', '2025-01-15', 1),
                                                                                                                 ('Lucia', 'Fernandez', 'Gomez', '72345689', 'lucia.fernandez@bruma.pe', 'Cajero', '2024-11-20', 1),
                                                                                                                 ('Miguel', 'Torres', 'Vargas', '73456890', 'miguel.torres@bruma.pe', 'Barista', '2023-05-10', 1),
                                                                                                                 ('Rosa', 'Castillo', 'Mendoza', '74568901', 'rosa.castillo@bruma.pe', 'Mozo', '2026-02-01', 1),
                                                                                                                 ('Jorge', 'Salinas', 'Rojas', '75689012', 'jorge.salinas@bruma.pe', 'Administrador', '2022-08-05', 1),
                                                                                                                 ('Carmen', 'Vidal', 'Soto', '76890123', 'carmen.vidal@bruma.pe', 'Mozo', '2026-05-10', 1);

INSERT INTO productos (nombre, categoria, descripcion, precio, stock, estado) VALUES
-- Bebidas Calientes
('Latte Vainilla', 'Bebidas Calientes', 'Espresso con leche vaporizada y sirope de vainilla', 10.50, 40, 1),
('Cappuccino Clásico', 'Bebidas Calientes', 'Espresso con partes iguales de leche vaporizada y espuma', 9.50, 50, 1),
('Te Matcha Latte', 'Bebidas Calientes', 'Té verde matcha orgánico con leche de almendras', 12.00, 30, 1),

-- Bebidas Frías
('Limonada con Menta', 'Bebidas Frías', 'Jugo de limón fresco con hojas de menta y hielo', 7.00, 60, 1),
('Iced Caramel Macchiato', 'Bebidas Frías', 'Espresso frío con leche y un toque de caramelo', 13.50, 45, 1),
('Smoothie de Frutos Rojos', 'Bebidas Frías', 'Mezcla de fresas frescas y arándanos con yogurt', 15.00, 25, 1),

-- Postres
('Porcion Torta de Chocolate', 'Postres', 'Bizcocho húmedo de cacao con fudge artesanal', 12.00, 20, 1),
('Cheesecake de Maracuya', 'Postres', 'Tarta de queso con base de galleta y jalea de maracuyá', 14.00, 15, 1),
('Alfajor de Maicena', 'Postres', 'Relleno con abundante manjar blanco espolvoreado con azúcar', 4.50, 40, 1),

-- Snacks
('Empanada de Carne', 'Snacks', 'Masa horneada rellena de guiso de carne, huevo y aceituna', 6.50, 35, 1),
('Sandwich Mixto', 'Snacks', 'Pan de molde con jamón glaseado y queso edam derretido', 8.00, 30, 1),
('Croissant de Mantequilla', 'Snacks', 'Pan hojaldrado clásico ideal para acompañar el café', 5.00, 40, 1);

INSERT INTO mesas (numero_mesa, capacidad, ubicacion, estado_mesa, estado) VALUES
                                                                               (1, 2, 'Salón Principal', 1, 1),
                                                                               (2, 2, 'Salón Principal', 2, 1),
                                                                               (3, 4, 'Junto a la Ventana', 1, 1),
                                                                               (4, 4, 'Junto a la Ventana', 1, 1),
                                                                               (5, 6, 'Terraza', 2, 1),
                                                                               (6, 6, 'Terraza', 3, 1),
                                                                               (7, 2, 'Terraza', 1, 1),
                                                                               (8, 8, 'Salón Privado', 1, 1);
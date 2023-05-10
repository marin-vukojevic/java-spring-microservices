CREATE TABLE orders
(
    uuid          UUID,
    customer_uuid UUID NOT NULL,
    total_amount  INT  NOT NULL,
    CONSTRAINT orders_pk PRIMARY KEY (uuid)
);

CREATE TABLE order_items
(
    order_uuid UUID NOT NULL,
    item_uuid  UUID,
    item_count INT  NOT NULL,
    CONSTRAINT order_items_pk PRIMARY KEY (order_uuid, item_uuid),
    CONSTRAINT order_items_order_uuid_fk FOREIGN KEY (order_uuid) REFERENCES orders (uuid)
);

run:
	docker compose up

run-detached:
	docker compose up -d

down:
	docker compose down

stop:
	docker compose stop

restart:
	docker compose restart

status:
	docker compose ps

build:
	docker compose build
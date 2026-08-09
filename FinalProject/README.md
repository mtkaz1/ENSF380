# Paws & Care Veterinary Clinic

This program uses JDBC to connect to PostgreSQL. The connection settings are
stored in `db.properties` so the database name, username, or password can be
changed without modifying the Java code.

## Database Setup

The PostgreSQL role `oop` must use the password `ucalgary`. If the role does
not exist, create it while logged in as `postgres`:

```sql
CREATE ROLE oop WITH LOGIN CREATEDB PASSWORD 'ucalgary';
```

If `oop` already exists but cannot create databases, an administrator can
run:

```sql
ALTER ROLE oop CREATEDB;
```

Then run the supplied SQL file as `oop` from the `FinalProject` folder:

```bash
psql -U oop -d postgres -f clinic_database.sql
```

The file creates `vet_clinic`, connects to it, and creates the clinic tables
and starting records. Run it only once on a fresh PostgreSQL installation.

## Compile and Run

From the `FinalProject` folder in Git Bash (Or you can modify for another terminal):

```bash
javac -cp "lib/postgresql-42.7.13.jar" -d bin edu/ucalgary/oop/*.java
java -cp "bin;lib/postgresql-42.7.13.jar" edu.ucalgary.oop.Main
```

## Menu Options

Enter a menu number and press Enter:

- `1` **View staff** - Display all veterinarians and receptionists.
- `2` **Add staff** - Register a new veterinarian or receptionist.
- `3` **Delete staff** - Delete a staff member using their ID.
- `4` **View owners** - Display all owners and their contact information.
- `5` **Add owner** - Register a new owner.
- `6` **Delete owner** - Delete an owner, their pets, and appointments.
- `7` **View pets** - Display all dogs and cats.
- `8` **Add pet** - Register a dog or cat under an existing owner.
- `9` **Delete pet** - Delete a pet and its appointments.
- `10` **View appointments** - Display all scheduled appointments.
- `11` **Schedule appointment** - Book a pet with a veterinarian.
- `12` **Cancel appointment** - Cancel an appointment using its ID.
- `0` **Exit** - Close the program.

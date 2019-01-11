-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema slipCoin
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema slipCoin
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `slipCoin` DEFAULT CHARACTER SET utf8 ;
USE `slipCoin` ;

-- -----------------------------------------------------
-- Table `slipCoin`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `slipCoin`.`users` (
  `idUser` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NULL DEFAULT NULL,
  `password` VARCHAR(150) NULL DEFAULT NULL,
  PRIMARY KEY (`idUser`))
ENGINE = InnoDB
AUTO_INCREMENT = 6
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `slipCoin`.`compte`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `slipCoin`.`compte` (
  `numeroCompte` VARCHAR(45) NULL DEFAULT NULL,
  `solde` FLOAT NULL DEFAULT NULL,
  `idUser` INT(11) NOT NULL,
  PRIMARY KEY (`idUser`),
  CONSTRAINT `fk_compte_users1`
    FOREIGN KEY (`idUser`)
    REFERENCES `slipCoin`.`users` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `slipCoin`.`entreprise`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `slipCoin`.`entreprise` (
  `nom` VARCHAR(45) NULL DEFAULT NULL,
  `description` TEXT NULL DEFAULT NULL,
  `produits` TEXT NULL DEFAULT NULL,
  `position` VARCHAR(10) NULL DEFAULT '0;0',
  `idUser` INT(11) NOT NULL,
  PRIMARY KEY (`idUser`),
  INDEX `fk_entreprise_users_idx` (`idUser` ASC),
  CONSTRAINT `fk_entreprise_users`
    FOREIGN KEY (`idUser`)
    REFERENCES `slipCoin`.`users` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `slipCoin`.`personne`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `slipCoin`.`personne` (
  `nom` VARCHAR(45) NULL DEFAULT NULL,
  `prenom` VARCHAR(45) NULL DEFAULT NULL,
  `dateNaissance` VARCHAR(45) NULL DEFAULT NULL,
  `idUser` INT(11) NOT NULL,
  PRIMARY KEY (`idUser`),
  INDEX `fk_table1_users1_idx` (`idUser` ASC),
  CONSTRAINT `fk_table1_users1`
    FOREIGN KEY (`idUser`)
    REFERENCES `slipCoin`.`users` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `slipCoin`.`transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `slipCoin`.`transaction` (
  `idTransaction` INT(11) NOT NULL AUTO_INCREMENT,
  `valeur` FLOAT NULL DEFAULT NULL,
  `idCrediteur` INT(11) NOT NULL,
  `idDebiteur` INT(11) NOT NULL,
  PRIMARY KEY (`idTransaction`, `idCrediteur`, `idDebiteur`),
  INDEX `fk_transaction_compte1_idx` (`idCrediteur` ASC),
  INDEX `fk_transaction_compte2_idx` (`idDebiteur` ASC),
  CONSTRAINT `fk_transaction_compte1`
    FOREIGN KEY (`idCrediteur`)
    REFERENCES `slipCoin`.`compte` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_transaction_compte2`
    FOREIGN KEY (`idDebiteur`)
    REFERENCES `slipCoin`.`compte` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;

USE `slipCoin` ;

-- -----------------------------------------------------
-- procedure insertEntreprise
-- -----------------------------------------------------

DELIMITER $$
USE `slipCoin`$$
CREATE DEFINER=`wef`@`%` PROCEDURE `insertEntreprise`(IN `argusername` VARCHAR(45), IN `argpassword` VARCHAR(150), IN `argnom` VARCHAR(45), IN `argdescription` TEXT, 
IN `argproduits` TEXT, IN `argposition` VARCHAR(10), IN `argNumeroCompte` VARCHAR(45))
begin 

    declare argid int(3);

    INSERT INTO users (username, password) VALUES (argusername, argpassword);

	INSERT INTO entreprise (nom, description, produits, position, idUser)
	VALUES (argnom, argdescription, argproduits, argposition, LAST_INSERT_ID());
    
	INSERT INTO compte (numeroCompte, solde, idUser) VALUES (argnumeroCompte, 0, LAST_INSERT_ID());

	SELECT LAST_INSERT_ID();

end$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure insertPersonne
-- -----------------------------------------------------

DELIMITER $$
USE `slipCoin`$$
CREATE DEFINER=`wef`@`%` PROCEDURE `insertPersonne`(IN `argusername` VARCHAR(45), IN `argpassword` VARCHAR(150), IN `argnom` VARCHAR(45), 
IN `argprenom` VARCHAR(45), IN `dateNaissance`  VARCHAR(45), IN `argnumeroCompte` VARCHAR (45))
begin 

    declare argid int(3);

    INSERT INTO users (username, password) VALUES (argusername, argpassword);

	INSERT INTO personne (nom, prenom, dateNaissance, idUser)
	VALUES (argnom, argprenom, dateNaissance, LAST_INSERT_ID());
    
    INSERT INTO compte (numeroCompte, solde, idUser) VALUES (argnumeroCompte, 0, LAST_INSERT_ID());
	
	SELECT LAST_INSERT_ID();

end$$

DELIMITER ;

-- -----------------------------------------------------
-- procedure transaction
-- -----------------------------------------------------

DELIMITER $$
USE `slipCoin`$$
CREATE DEFINER=`wef`@`%` PROCEDURE `transaction`(IN `argnumercoCompteDebiteur` VARCHAR(45), IN `argnumercoCompteCrediteur` VARCHAR(45), IN `argvaleur` float)
BEGIN

DECLARE varidDebiteur int(6);
DECLARE varidCrediteur int(6);

SELECT idUser INTO varidDebiteur FROM compte WHERE numeroCompte = argnumercoCompteDebiteur;
SELECT idUser INTO varidCrediteur FROM compte WHERE numeroCompte = argnumercoCompteCrediteur;

INSERT INTO transaction (valeur, idCrediteur, idDebiteur) VALUES (argvaleur, varidCrediteur, varidDebiteur);

UPDATE compte SET solde = solde - argvaleur WHERE idUser = varidCrediteur;
UPDATE compte SET solde = solde + argvaleur WHERE idUser = varidDebiteur;

END$$

DELIMITER ;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

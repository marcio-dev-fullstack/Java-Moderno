provider "aws" {
  region = var.aws_region
}

variable "aws_region" {
  default = "us-east-1"
}

resource "aws_ecr_repository" "devhub_ecr" {
  name                 = "dev-knowledge-ai-hub"
  image_tag_mutability = "MUTABLE"
}

resource "aws_db_instance" "devhub_rds" {
  allocated_storage    = 20
  engine               = "postgres"
  engine_version       = "16"
  instance_class       = "db.t4g.micro"
  db_name              = "devhubdb"
  username             = "postgres"
  password             = "postgrespassword"
  skip_final_snapshot  = true
}

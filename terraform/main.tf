terraform {
  required_version = ">= 1.5.0"
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }
}

provider "aws" {
  region = var.aws_region
}

variable "aws_region" {
  default = "us-east-1"
}

# Amazon ECR Repository
resource "aws_ecr_repository" "app_repo" {
  name                 = "dev-knowledge-ai-hub"
  image_tag_mutability = "MUTABLE"

  image_scanning_configuration {
    scan_on_push = true
  }
}

# RDS PostgreSQL Database (with pgvector support)
resource "aws_db_instance" "postgres" {
  allocated_storage    = 20
  engine               = "postgres"
  engine_version       = "16.1"
  instance_class       = "db.t4g.micro"
  db_name              = "devhubdb"
  username             = "postgres"
  password             = var.db_password
  skip_final_snapshot  = true
  publicly_accessible  = false
}

variable "db_password" {
  description = "Master password for PostgreSQL database"
  type        = string
  sensitive   = true
}

output "ecr_repository_url" {
  value = aws_ecr_repository.app_repo.repository_url
}

output "rds_endpoint" {
  value = aws_db_instance.postgres.endpoint
}

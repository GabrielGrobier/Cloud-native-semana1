terraform {
    required_version = ">= 1.0"

    required_providers {
        aws = {
            source  = "hashicorp/aws"
            version = ">= 6.37.0"
        }
    }
}
provider "aws" {
    region = var.region
  
}

module "ec2_SG" {
    source = "terraform-aws-modules/security-group/aws"
    version ="5.3.1"
    name = "ec2_security_group"
    vpc_id = var.vpc_id
    ingress_cidr_blocks = ["0.0.0.0/0"]
    ingress_rules = ["ssh-tcp", "http-80-tcp"]
    egress_cidr_blocks = ["0.0.0.0/0"]
    egress_rules = ["all-all"]
  
}
module "mainly-ec2" {
    source = "terraform-aws-modules/ec2-instance/aws"
    version = "6.4.0"
    name = "mainly-ec2"
    ami = var.ami_id
    instance_type = "t4g.micro"
    key_name = "cloud_computing"
    subnet_id = var.subnet_id
        vpc_security_group_ids = [module.ec2_SG.security_group_id]
        associate_public_ip_address = true
        user_data = <<-EOF
            #!/bin/bash
            set -eux

            apt-get update
            apt-get install -y curl

            curl -fsSL https://get.docker.com -o docker.sh
            sh docker.sh

            apt-get update
            apt-get install -y docker-compose-plugin

            systemctl enable docker
            systemctl start docker

            if id ubuntu >/dev/null 2>&1; then
                usermod -aG docker ubuntu
            fi
        EOF
  
}
output "mainly_ec2_ip" {
    value = module.mainly-ec2.public_ip
    description = "Public IP address of the mainly-ec2 instance"
  
}
variable "vpc_id" {
    type = string
    default = "vpc-00c70dff27606877b"
}
variable "region" {
    type = string
    default = "us-east-1"
  
}
variable "ami_id" {
    type = string
    default = "ami-07ad186bc37b8dac4"
    description = "ubuntu - arm "
  
}

variable "subnet_id" {
    type = string
    default = "subnet-09b3c2578b5245b8e"
    description = "Subnet ID where the EC2 instance will be launched"
}
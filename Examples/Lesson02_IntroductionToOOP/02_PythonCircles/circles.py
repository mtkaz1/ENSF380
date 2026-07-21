# Copyright Ann Barcomb, 2025
# Licensed under GPL v3
# See LICENSE.txt for more information.

def area(radius):
    return 3.14 * radius ** 2

def perimeter(radius):
    return 2 * 3.14 * radius

def describe(radius, colour):
    return f"A {colour} circle with radius {radius}"

# Define attributes for multiple circles
circle1_radius = 5
circle1_colour = "red"
circle2_radius = 10
circle2_colour = "blue"

print("Non-OOP Example:")
print(describe(circle1_radius, circle1_colour))
print("Area:", area(circle1_radius))
print("Perimeter:", perimeter(circle1_radius))

print(describe(circle2_radius, circle2_colour))
print("Area:", area(circle2_radius))
print("Perimeter:", perimeter(circle2_radius))


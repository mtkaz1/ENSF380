# Copyright Ann Barcomb, 2025
# Licensed under GPL v3
# See LICENSE.txt for more information.

# Circle-related functions
def circle_area(radius):
    return 3.14 * radius ** 2

def circle_perimeter(radius):
    return 2 * 3.14 * radius

def describe_circle(radius, colour):
    return f"A {colour} circle with radius {radius}"

# Square-related functions
def square_area(side):
    return side ** 2

def square_perimeter(side):
    return 4 * side

def describe_square(side, colour):
    return f"A {colour} square with side length {side}"


# Define attributes for a circle and a square
circle_radius = 5
circle_colour = "red"
square_side = 4
square_colour = "blue"

print("Non-OOP Example:")
print(describe_circle(circle_radius, circle_colour))
print("Area:", circle_area(circle_radius))
print("Perimeter:", circle_perimeter(circle_radius))

print(describe_square(square_side, square_colour))
print("Area:", square_area(square_side))
print("Perimeter:", square_perimeter(square_side))


# Copyright Ann Barcomb, 2025
# Licensed under GPL v3
# See LICENSE.txt for more information.

# This code contains a deliberate error which is discussed in the lecture.

# Functions for circle operations
def area(radius, units):
    return f"{3.14 * radius ** 2} {units}²"

def perimeter(radius, units):
    return f"{2 * 3.14 * radius} {units}"

def describe(radius, colour, material):
    return f"A {colour} circle made of {material} with a radius of {radius}"

# Define circles
circle1_radius = 5
circle1_colour = "red"
circle1_material = "plastic"
circle1_units = "cm"

circle2_radius = 7
circle2_colour = "blue"
circle2_material = "metal"
circle2_units = "cm"

circle3_radius = 3
circle3_colour = "green"
circle3_material = "wood"
circle3_units = "cm"

circle4_radius = 9
circle4_colour = "yellow"
circle4_material = "rubber"
circle4_units = "cm"

circle5_radius = 6
circle5_colour = "purple"
circle5_material = "glass"
circle5_units = "cm"

circle6_radius = 4
circle6_colour = "orange"
circle6_material = "ceramic"
circle6_units = "cm"

circle7_radius = 8
circle7_colour = "pink"
circle7_material = "stone"
circle7_units = "cm"

circle8_radius = 10
circle8_colour = "white"
circle8_material = "paper"
circle8_units = "cm"

circle9_radius = 2
circle9_colour = "black"
circle9_material = "foam"
circle9_units = "cm"

circle10_radius = 12
circle10_colour = "brown"
circle10_material = "leather"
circle10_units = "cm"

# Print details for each circle
print(describe(circle1_radius, circle1_colour, circle1_material))
print("Area:", area(circle1_radius, circle1_units))
print("Perimeter:", perimeter(circle1_radius, circle1_units))
print()

print(describe(circle2_radius, circle2_colour, circle2_material))
print("Area:", area(circle1_radius, circle2_units)) # There is an error here
print("Perimeter:", perimeter(circle2_radius, circle2_units))
print()

print(describe(circle3_radius, circle3_colour, circle3_material))
print("Area:", area(circle3_radius, circle3_units))
print("Perimeter:", perimeter(circle3_radius, circle3_units))
print()

print(describe(circle4_radius, circle4_colour, circle4_material))
print("Area:", area(circle4_radius, circle4_units))
print("Perimeter:", perimeter(circle4_radius, circle4_units))
print()

print(describe(circle5_radius, circle5_colour, circle5_material))
print("Area:", area(circle5_radius, circle5_units))
print("Perimeter:", perimeter(circle5_radius, circle5_units))
print()

print(describe(circle6_radius, circle6_colour, circle6_material))
print("Area:", area(circle6_radius, circle6_units))
print("Perimeter:", perimeter(circle6_radius, circle6_units))
print()

print(describe(circle7_radius, circle7_colour, circle7_material))
print("Area:", area(circle7_radius, circle7_units))
print("Perimeter:", perimeter(circle7_radius, circle7_units))
print()

print(describe(circle8_radius, circle8_colour, circle8_material))
print("Area:", area(circle8_radius, circle8_units))
print("Perimeter:", perimeter(circle8_radius, circle8_units))
print()

print(describe(circle9_radius, circle9_colour, circle9_material))
print("Area:", area(circle9_radius, circle9_units))
print("Perimeter:", perimeter(circle9_radius, circle9_units))
print()

print(describe(circle10_radius, circle10_colour, circle10_material))
print("Area:", area(circle10_radius, circle10_units))
print("Perimeter:", perimeter(circle10_radius, circle10_units))
print()


# Copyright Ann Barcomb, 2025
# Licensed under GPL v3
# See LICENSE.txt for more information.

class Circle:
    def __init__(self, radius, colour):
        self.radius = radius
        self.colour = colour

    def area(self):
        return 3.14 * self.radius ** 2

    def perimeter(self):
        return 2 * 3.14 * self.radius

    def describe(self):
        return f"A {self.colour} circle with radius {self.radius}"

# Create multiple Circle instances
circle1 = Circle(5, "red")
circle2 = Circle(10, "blue")

print("OOP Example:")
print(circle1.describe())
print("Area:", circle1.area())
print("Perimeter:", circle1.perimeter())

print(circle2.describe())
print("Area:", circle2.area())
print("Perimeter:", circle2.perimeter())


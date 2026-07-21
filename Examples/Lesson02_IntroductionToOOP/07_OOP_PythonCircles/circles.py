# Copyright Ann Barcomb, 2025
# Licensed under GPL v3
# See LICENSE.txt for more information.

class Circle:
    def __init__(self, radius):
        self.radius = radius
    def area(self):
        return 3.14 * self.radius ** 2
    def perimeter(self):
        return 2 * 3.14 * self.radius

thecircle = Circle(5)
print("Area:", thecircle.area())
print("Perimeter:", thecircle.perimeter())

